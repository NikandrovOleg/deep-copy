package graphParts

interface VertexProperty<T : Any> {
    val name: String
    val vertex: Vertex<T>
}