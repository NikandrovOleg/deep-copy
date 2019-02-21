package modelImpl.vertices

import graphParts.vertices.ListVertex
import graphParts.Color
import graphParts.Vertex

data class ListVertexImpl(
    override var replica: List<*>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ListVertex