package modelImpl.vertices

import graphParts.vertices.ObsoleteInitVertex
import graphParts.Color
import graphParts.Vertex

data class ObsoleteInitVertexImpl<T : Any>(
    override var replica: T? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Any, Vertex<*, *>> = mutableMapOf()
) : ObsoleteInitVertex<T>