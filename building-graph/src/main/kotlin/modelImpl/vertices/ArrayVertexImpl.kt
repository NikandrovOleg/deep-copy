package modelImpl.vertices

import graphParts.vertices.ArrayVertex
import graphParts.Color
import graphParts.Vertex

data class ArrayVertexImpl<K: Any?>(
    override val arrayType: Class<K>,
    override var replica: Array<K>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ArrayVertex<K>