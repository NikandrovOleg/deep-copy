package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.SetVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class SetVertexImpl(
    override val kClass: KClass<out Set<*>>,
    override val original: Set<*>?,
    override var color: Color = Color.WHITE,
    override var copy: Set<*>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : SetVertex, VertexImpl<Set<*>, Int>()