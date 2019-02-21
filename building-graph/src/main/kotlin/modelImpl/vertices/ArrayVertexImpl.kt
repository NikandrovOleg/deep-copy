package modelImpl.vertices

import graphParts.vertices.ArrayVertex
import graphParts.Color
import graphParts.Vertex

data class ArrayVertexImpl(
    override var replica: Array<*>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ArrayVertex