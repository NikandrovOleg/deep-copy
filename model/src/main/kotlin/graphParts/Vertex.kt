package graphParts

interface Vertex<T : Any, V : Any> {
    var color: Color
    var replica: T?
    val properties: MutableMap<V, Vertex<*, *>>
}