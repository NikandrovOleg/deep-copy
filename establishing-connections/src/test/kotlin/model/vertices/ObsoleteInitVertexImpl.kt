package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ObsoleteInitVertex

data class ObsoleteInitVertexImpl<T: Any>(
    override var replica: T? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Any, Vertex<*, *>> = mutableMapOf()
) : ObsoleteInitVertex<T> {
    override fun initReplica() {}
}