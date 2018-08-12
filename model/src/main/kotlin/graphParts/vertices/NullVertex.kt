package graphParts.vertices

import graphParts.Vertex

interface NullVertex<T : Any>: Vertex<T, Any> {
    override var copy: T?
        get() = null
        set(value) {}
}