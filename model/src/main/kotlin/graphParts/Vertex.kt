package graphParts

interface Vertex<T> {
    val color: Color
    val properties: List<VertexProperty<Any>>
    val copy: T
}