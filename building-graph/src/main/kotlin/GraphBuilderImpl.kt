import graphParts.Graph
import graphParts.Vertex
import modelImpl.GraphImpl
import modelImpl.VertexImpl
import modelImpl.vertices.*
import kotlin.reflect.KClass

import kotlin.reflect.full.memberProperties
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
                val vertex = NullVertexImpl(kClass)
                vertices.add(vertex)
                vertex
            }
            currentObj is Array<*> -> {
                val vertex = vertexOfArray(currentObj, vertices)
                vertex
            }
            currentObj is List<*> -> {
                val vertex = vertexOfList(currentObj, vertices)
                vertex
            }
            else -> {
                val vertex = creationVertexFunctions.getOrDefault(currentObj::class, vertexOfComplexClass).
                    invoke(currentObj, vertices)
                vertex
            }
        }
    }

    private val vertexOfComplexClass = { currentObj: Any, vertices: MutableList<VertexImpl<*, *>> ->
        val vertex = ComplexVertexImpl(currentObj::class, currentObj)
        vertices.add(vertex)
        vertex.properties.putAll(vertex.kClass.memberProperties.associate {
            val javaProp = vertex.kClass.java.getDeclaredField(it.name)
            javaProp.isAccessible = true
            Pair(it.name, dfsVisit(javaProp.get(vertex.original), it.returnType.jvmErasure, vertices))
        })
        vertex
    }

    private val vertexOfPrimitiveType = { currentObj: Any, vertices: MutableList<VertexImpl<*, *>> ->
        val vertex = PrimitiveVertexImpl(currentObj::class, currentObj, currentObj)
        vertices.add(vertex)
        vertex
    }

    private val vertexOfArray = { currentArray: Array<*>, vertices: MutableList<VertexImpl<*, *>> ->
        val vertex = ArrayVertexImpl(currentArray::class, currentArray)
        vertices.add(vertex)
        val arrayType = currentArray::class.java.componentType.kotlin
        for (i in currentArray.indices) { vertex.properties[i] = dfsVisit(currentArray[i], arrayType, vertices) }
        vertex
    }

    private val vertexOfList = { currentArray: List<*>, vertices: MutableList<VertexImpl<*, *>> ->
        val vertex = ListVertexImpl(currentArray::class, currentArray)
        vertices.add(vertex)
        for (i in currentArray.indices) { vertex.properties[i] = dfsVisit(currentArray[i], Any::class, vertices) }
        vertex
    }

    private val creationVertexFunctions = hashMapOf(
            String::class to vertexOfPrimitiveType,
            Double::class to vertexOfPrimitiveType,
            Float::class to vertexOfPrimitiveType,
            Long::class to vertexOfPrimitiveType,
            Int::class to vertexOfPrimitiveType,
            Short::class to vertexOfPrimitiveType,
            Byte::class to vertexOfPrimitiveType,
            Char::class to vertexOfPrimitiveType,
            Boolean::class to vertexOfPrimitiveType
    )
}
