package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.MapVertex

data class MapVertexImpl(
    override var replica: Map<*, *>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : MapVertex