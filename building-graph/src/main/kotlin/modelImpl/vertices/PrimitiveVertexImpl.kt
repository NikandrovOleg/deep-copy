package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.PrimitiveVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class PrimitiveVertexImpl<T: Any>(
        override val kClass: KClass<out T>,
        override val original: T,
        override var replica: T?,
        override var color: Color = Color.WHITE,
        override val properties: MutableMap<Any, Vertex<*, *>> = mutableMapOf()
) : PrimitiveVertex<T>, VertexImpl<T, Any>()