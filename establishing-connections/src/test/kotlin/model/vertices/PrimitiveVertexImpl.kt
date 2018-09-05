package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PrimitiveVertex
import kotlin.reflect.KClass

data class PrimitiveVertexImpl<T: Any>(
    override val kClass: KClass<out T>,
    override var replica: T?,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Any, Vertex<*, *>> = mutableMapOf()
) : PrimitiveVertex<T>