package modelImpl

import graphParts.Vertex

abstract class VertexImpl<T: Any, V: Any> : Vertex<T, V> {
    abstract val original: T?
}