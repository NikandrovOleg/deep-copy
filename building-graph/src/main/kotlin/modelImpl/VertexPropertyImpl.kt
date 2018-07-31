package modelImpl

import graphParts.Vertex
import graphParts.VertexProperty

class VertexPropertyImpl<T : Any>(override val name: String, override val vertex: Vertex<T>) : VertexProperty<T>