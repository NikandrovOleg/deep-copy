package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.SetVertex

data class SetVertexImpl(
    override var replica: Set<*>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : SetVertex