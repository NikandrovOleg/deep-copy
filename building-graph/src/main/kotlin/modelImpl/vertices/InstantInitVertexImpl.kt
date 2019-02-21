package modelImpl.vertices

import graphParts.vertices.InstantInitVertex
import graphParts.Color
import graphParts.Vertex

data class InstantInitVertexImpl<T : Any>(
    override var replica: T?,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : InstantInitVertex<T>