import graphParts.Graph
import graphParts.Vertex
import modelImpl.GraphImpl
import modelImpl.VertexImpl
import modelImpl.vertices.ComplexVertexImpl
import modelImpl.vertices.NullVertexImpl
import modelImpl.vertices.PrimitiveVertexImpl
import kotlin.reflect.KClass

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

object GraphBuilderImpl: GraphBuilder {
    private val vertices = mutableListOf<VertexImpl<*, *>>()

    override fun buildGraph(obj: Any?): Graph {
        if (obj != null) {
            dfsVisit(obj, obj::class)
        }
        return GraphImpl(vertices)
    }

    private fun dfsVisit(currentObj: Any?, kClass: KClass<*>): Vertex<*, *> {
        return when {
            vertices.any { it.original === currentObj && it.kClass == kClass }
                -> vertices.first { it.original === currentObj }
            currentObj == null -> {
                val vertex = NullVertexImpl(kClass)
                vertices.add(vertex)
                vertex
            }
            else -> {
                val vertex = creationVertexFunctions.getOrDefault(kClass, vertexOfComplexClass).
                        invoke(currentObj, kClass)
                vertex
            }
        }
    }

    private val vertexOfComplexClass = { currentObj: Any, kClass: KClass<*> ->
        val vertex = ComplexVertexImpl(kClass, currentObj)
        vertices.add(vertex)
        vertex.properties.putAll(vertex.kClass.memberProperties.associate {
            val javaProp = vertex.kClass.java.getDeclaredField(it.name)
            javaProp.isAccessible = true
            Pair(it.name, dfsVisit(javaProp.get(vertex.original), it.returnType.jvmErasure))
        })
        vertex
    }

    private val vertexOfPrimitiveType = { currentObj: Any, kClass: KClass<*> ->
        val vertex = PrimitiveVertexImpl(kClass, currentObj, currentObj)
        vertices.add(vertex)
        vertex
    }

    private val creationVertexFunctions=hashMapOf(
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
