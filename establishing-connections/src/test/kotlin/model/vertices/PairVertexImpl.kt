package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PairVertex
import kotlin.reflect.KClass

data class PairVertexImpl(
    override val kClass: KClass<out Pair<*, *>>,
    override var color: Color = Color.WHITE,
    override var replica: Pair<*, *>? = null,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : PairVertex