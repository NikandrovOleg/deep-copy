package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PairVertex

class PairVertexImpl<K: Any?, V: Any?>(
    override var replica: Pair<K, V>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : PairVertex<K, V>