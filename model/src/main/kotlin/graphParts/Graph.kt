package graphParts

interface Graph {
    val vertexes: List<Vertex<*>>

    fun getParents(vertex: Vertex<Any>): List<Vertex<*>>
    fun getChildren(vertex: Vertex<Any>): List<Vertex<*>>
}