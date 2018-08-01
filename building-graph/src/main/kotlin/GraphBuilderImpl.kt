import graphParts.Graph
import graphParts.Vertex
import modelImpl.GraphImpl
import modelImpl.VertexImpl
import modelImpl.VertexPropertyImpl
import kotlin.reflect.full.memberProperties

object GraphBuilderImpl: GraphBuilder {
    override fun buildGraph(obj: Any): Graph {
        val vertexes = mutableListOf<VertexImpl<*>>()

        fun dfsVisit(currentObj: Any): Vertex<*> {
            if (vertexes.any { it.original === currentObj }) {
                return vertexes.first { it.original === currentObj }
            } else {
                val vertex: VertexImpl<Any> = VertexImpl(currentObj::class, currentObj)
                vertexes.add(vertex)
                if (currentObj::class !in leavesClasses) {
                    vertex.properties.addAll(currentObj::class.memberProperties
                                    .filter {
                                        val javaProp = currentObj::class.java.getDeclaredField(it.name)
                                        javaProp.isAccessible = true
                                        javaProp.get(currentObj) != null
                                    }
                                    .map {
                                        val javaProp = currentObj::class.java.getDeclaredField(it.name)
                                        javaProp.isAccessible = true
                                        VertexPropertyImpl(it.name, dfsVisit(javaProp.get(currentObj)))
                            })
                } else {
                    vertex.copy = currentObj
                }
                return vertex
            }
        }

        dfsVisit(obj)
        return GraphImpl(vertexes)
    }
}

val leavesClasses = listOf(String::class, Double::class, Float::class, Long::class, Int::class, Short::class,
        Byte::class, Char::class, Boolean::class)