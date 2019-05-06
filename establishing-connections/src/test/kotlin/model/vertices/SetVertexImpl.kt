package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.SetVertex

data class SetVertexImpl<K: Any?>(
    override var replica: Set<K>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : SetVertex<K>