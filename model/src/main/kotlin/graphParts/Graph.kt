package graphParts

interface Graph<T: Any> {
    val vertices: List<Vertex<*, *>>
    fun getRootReplica(): T?
}