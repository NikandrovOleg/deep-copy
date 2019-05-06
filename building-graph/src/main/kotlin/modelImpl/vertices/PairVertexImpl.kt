package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PairVertex

class PairVertexImpl<K: Any?, S: Any?>(
    override var replica: Pair<K, S>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : PairVertex<K, S>