import graphParts.Graph
import modelImpl.GraphImpl
import modelImpl.VertexWithOriginal

class GraphBuilder {

    val vertices = mutableListOf<VertexWithOriginal<*, *>>()

    fun <T : Any> buildGraph(obj: T): Graph<T> {
        val root = dfsVisit(obj)
        return GraphImpl.of(vertices, root)
    }

    private fun <T : Any> dfsVisit(currentObj: T?): VertexWithOriginal<T, *> {
        val vertex = VertexFactory.createVertexOfObject(currentObj)
        if (vertices.any { it.original === currentObj }) {
            return vertices.find { it.original === currentObj } as VertexWithOriginal<T, *>
        }
        vertices.add(vertex)
        vertex.fillProperties(::dfsVisit)
        return vertex
    }
}
