package graphParts.vertices

import graphParts.Vertex

interface NullVertex<T : Any>: Vertex<T, Any> {
    override var replica: T?
        get() = null
        set(value) {}
}