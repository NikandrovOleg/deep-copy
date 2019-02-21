package graphParts.vertices

import graphParts.Vertex

interface ObsoleteInitVertex<T : Any> : Vertex<T, Any> {
    override val properties: MutableMap<Any, Vertex<*, *>>
        get() = mutableMapOf()
}