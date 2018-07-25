package graphParts

interface Graph {
    val vertexes: List<Vertex<*>>

    fun getParents(vertex: Vertex<Any>): List<Vertex<*>>
    fun getChildes(vertex: Vertex<Any>): List<Vertex<*>>
}