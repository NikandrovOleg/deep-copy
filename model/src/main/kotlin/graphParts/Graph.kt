package graphParts

interface Graph {
    val Vertexes: List<Vertex<Any>>

    fun getParents(vertex: Vertex<Any>): List<Vertex<Any>>
    fun getChilds(vertex: Vertex<Any>): List<Vertex<Any>>
}