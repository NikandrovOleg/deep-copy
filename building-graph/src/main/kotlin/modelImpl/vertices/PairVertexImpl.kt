package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PairVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

class PairVertexImpl(
    override val kClass: KClass<out Pair<*, *>>,
    override val original: Pair<*, *>?,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf(),
    override var color: Color = Color.WHITE,
    override var replica: Pair<*, *>? = null
) : PairVertex, VertexImpl<Pair<*, *>, String>()