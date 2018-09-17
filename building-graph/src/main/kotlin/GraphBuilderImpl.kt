import graphParts.Graph
import graphParts.Vertex
import modelImpl.GraphImpl
import modelImpl.VertexImpl
import modelImpl.vertices.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.jvmErasure

object GraphBuilderImpl: GraphBuilder {

    override fun buildGraph(obj: Any?): Graph {
        val vertices = mutableListOf<VertexImpl<*, *>>()
        if (obj != null) {
            dfsVisit(obj, obj::class, vertices)
        }
        return GraphImpl(vertices)
    }

    private fun dfsVisit(currentObj: Any?, kClass: KClass<*>, vertices: MutableList<VertexImpl<*, *>>): Vertex<*, *> {
        return when {
            vertices.any { it.original === currentObj && it.kClass == kClass }
                -> vertices.first { it.original === currentObj }
            currentObj == null -> {
                NullVertexImpl(kClass).also { vertices.add(it) }
            }
            currentObj is Array<*> -> {
                vertexOfArray(currentObj, vertices)
            }
            else -> {
                (creationVertexFunctions.filterKeys { currentObj::class.isSubclassOf(it) }.values.
                    firstOrNull()?: vertexOfComplexClass).invoke(currentObj, vertices)
            }
        }
    }

    private val vertexOfComplexClass = { currentObj: Any, vertices: MutableList<VertexImpl<*, *>> ->
        ComplexVertexImpl(currentObj::class, currentObj).also {
            vertices.add(it)
            configurePropertiesOfComplexClass(it, vertices)
        }
    }

    private fun configurePropertiesOfComplexClass (vertex: ComplexVertexImpl<*>, vertices: MutableList<VertexImpl<*, *>>) {
        vertex.properties.putAll(vertex.kClass.memberProperties.associate {
            Pair(it.name, dfsVisit(vertex.kClass.java.getDeclaredField(it.name).also { it.isAccessible = true }
                .get(vertex.original), it.returnType.jvmErasure, vertices))
        })
    }

    private val vertexOfPrimitiveType = { currentObj: Any, vertices: MutableList<VertexImpl<*, *>> ->
        PrimitiveVertexImpl(currentObj::class, currentObj, currentObj).also { vertices.add(it) }
    }

    private val vertexOfArray = { currentArray: Array<*>, vertices: MutableList<VertexImpl<*, *>> ->
        ArrayVertexImpl(currentArray::class, currentArray).also {
            vertices.add(it)
            val arrayType = currentArray::class.java.componentType.kotlin
            for (i in currentArray.indices) {
                it.properties[i] = dfsVisit(currentArray[i], arrayType, vertices)
            }
        }
    }

    private val vertexOfCollection = { currentObject: Any, vertices: MutableList<VertexImpl<*, *>> ->
        val currentCollection = currentObject as Collection<*>
        CollectionVertexImpl(currentCollection::class, currentCollection).also {
            vertices.add(it)
            for (i in currentCollection.indices) {
                it.properties[i] = dfsVisit(currentCollection.elementAt(i), Any::class, vertices)
            }
        }
    }

    private val vertexOfMap = { currentObj: Any, vertices: MutableList<VertexImpl<*, *>> ->
        val currentMap = currentObj as Map<*, *>
        MapVertexImpl(currentMap::class, currentMap).also {
            vertices.add(it)
            for (i in currentMap.entries.indices) {
                it.properties[i] = dfsVisit(currentMap.entries.elementAt(i), Any::class, vertices)
            }
        }
    }

    private val vertexOfEntry = { currentObj: Any, vertices: MutableList<VertexImpl<*, *>> ->
        val currentEntry = currentObj as Map.Entry<*, *>
        PairVertexImpl(currentEntry.toPair()::class, currentEntry.toPair()).also {
            vertices.add(it)
            it.properties["first"] = dfsVisit(currentEntry.key, Any::class, vertices)
            it.properties["second"] = dfsVisit(currentEntry.value, Any::class, vertices)
        }
    }

    private val creationVertexFunctions = hashMapOf(
        String::class to vertexOfPrimitiveType,
        Number::class to vertexOfPrimitiveType,
        Char::class to vertexOfPrimitiveType,
        Boolean::class to vertexOfPrimitiveType,
        Collection::class to vertexOfCollection,
        Map::class to vertexOfMap,
        Map.Entry::class to vertexOfEntry
    )
}
