package modelImpl.vertices

import graphParts.vertices.ListVertex
import graphParts.Color
import graphParts.Vertex

data class ListVertexImpl<K: Any?>(
    override var replica: List<K>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ListVertex<K>