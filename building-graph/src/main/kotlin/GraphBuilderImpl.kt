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
                if (currentObj::class !in leavesClasses) {
                    val vertex = VertexImpl(currentObj::class, currentObj,
                            properties = currentObj::class.memberProperties.map {
                                val javaProp = currentObj::class.java.getDeclaredField(it.name)
                                javaProp.isAccessible = true
                                VertexPropertyImpl(it.name, dfsVisit(javaProp.get(currentObj)))
                            })
                    vertexes.add(vertex)
                    return vertex
                }
                val vertex = VertexImpl(currentObj::class, currentObj, currentObj)
                vertexes.add(vertex)
                return vertex
            }
        }

        dfsVisit(obj)
        return GraphImpl(vertexes)
    }
}

val leavesClasses = listOf(String::class, Double::class, Float::class, Long::class, Int::class, Short::class,
        Byte::class, Char::class, Boolean::class)