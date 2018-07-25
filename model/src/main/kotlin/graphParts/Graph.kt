package graphParts

interface Graph {
    val Vertexes: List<Vertex<*>>

    fun getParents(vertex: Vertex<Any>): List<Vertex<*>>
    fun getChilds(vertex: Vertex<Any>): List<Vertex<*>>
}