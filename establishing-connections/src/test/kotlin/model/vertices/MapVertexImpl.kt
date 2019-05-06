package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.MapVertex

data class MapVertexImpl<K: Any?, V: Any?>(
    override var replica: Map<K, V>? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : MapVertex<K, V>