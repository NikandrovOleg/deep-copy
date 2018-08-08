import graphParts.Graph
import graphParts.Vertex
import modelImpl.GraphImpl
import modelImpl.VertexImpl
import modelImpl.VertexPropertyImpl
import kotlin.reflect.KClass

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

object GraphBuilderImpl: GraphBuilder {
    override fun buildGraph(obj: Any?): Graph {
        val vertexes = mutableListOf<VertexImpl<*>>()
        if (obj != null) {
            dfsVisit(obj, obj::class, vertexes)
        }
        return GraphImpl(vertexes)
    }

    private fun dfsVisit(currentObj: Any?, kclass: KClass<*>, vertexes: MutableList<VertexImpl<*>>): Vertex<*> {
        return if (vertexes.any { it.original === currentObj }) {
            vertexes.first { it.original === currentObj }
        } else {
            val vertex = VertexImpl(kclass, currentObj)
            vertexes.add(vertex)
            if (currentObj != null) {
                customizeFunctions.getOrDefault(kclass, vertexOfComplexClass)
                        .invoke(currentObj, vertex, vertexes)
            }
            vertex
        }
    }

    private val vertexOfComplexClass = { currentObj: Any, vertex: Vertex<*>, vertexes: MutableList<VertexImpl<*>> ->
        vertex.properties.addAll(currentObj::class.memberProperties
                .map {
                    val javaProp = currentObj::class.java.getDeclaredField(it.name)
                    javaProp.isAccessible = true
                    VertexPropertyImpl(it.name, dfsVisit(javaProp.get(currentObj), it.returnType.jvmErasure, vertexes))
                })
    }

    private val vertexOfPrimitiveType = { currentObj: Any, vertex: Vertex<Any>, _: MutableList<VertexImpl<*>>
        -> vertex.copy = currentObj }

    private val customizeFunctions= hashMapOf(
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
