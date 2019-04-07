package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PairVertex

class PairVertexImpl(
    override var replica: Pair<*, *>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : PairVertex