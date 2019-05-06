package modelImpl.vertices

import graphParts.vertices.SetVertex
import graphParts.Color
import graphParts.Vertex

data class SetVertexImpl<K: Any?>(
    override var replica: Set<K>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : SetVertex<K>