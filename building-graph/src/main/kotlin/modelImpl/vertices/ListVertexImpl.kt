package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ListVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class ListVertexImpl(
    override val kClass: KClass<out List<*>>,
    override val original: List<*>?,
    override var color: Color = Color.WHITE,
    override var copy: List<*>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ListVertex, VertexImpl<List<*>, Int>()