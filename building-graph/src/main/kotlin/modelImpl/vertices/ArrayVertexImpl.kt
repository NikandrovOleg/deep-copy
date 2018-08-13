package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ArrayVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class ArrayVertexImpl(
    override val kClass: KClass<out Array<*>>,
    override val original: Array<*>?,
    override var color: Color = Color.WHITE,
    override var copy: Array<*>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ArrayVertex, VertexImpl<Array<*>, Int>()