package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ArrayVertex

data class ArrayVertexImpl(
    override var replica: Array<*>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ArrayVertex