package modelImpl.vertices

import graphParts.vertices.MapVertex
import graphParts.Color
import graphParts.Vertex

data class MapVertexImpl(
    override var replica: Map<*, *>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : MapVertex