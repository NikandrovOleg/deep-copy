package graphParts.vertices

import graphParts.Vertex

interface LazyInitVertex<T : Any, V : Any> : Vertex<T, V> {
    fun initReplica()
}