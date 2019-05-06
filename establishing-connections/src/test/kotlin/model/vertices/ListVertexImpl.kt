package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ListVertex

data class ListVertexImpl<K: Any?>(
    override var replica: List<K>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ListVertex<K>