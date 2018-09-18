package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ArrayVertex
import kotlin.reflect.KClass

data class ArrayVertexImpl(
    override val kClass: KClass<out Array<*>>,
    override var color: Color = Color.WHITE,
    override var replica: Array<*>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : ArrayVertex