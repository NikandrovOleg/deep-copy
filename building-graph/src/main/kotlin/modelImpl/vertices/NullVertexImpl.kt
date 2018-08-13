package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.NullVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class NullVertexImpl<T: Any> (
        override val kClass: KClass<out T>,
        override val original: T? = null,
        override var color: Color = Color.WHITE,
        override val properties: MutableMap<Any, Vertex<*, *>> = mutableMapOf()
) : NullVertex<T>, VertexImpl<T, Any>()