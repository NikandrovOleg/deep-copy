package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ComplexVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class ComplexVertexImpl<T : Any>(
        override val kClass: KClass<out T>,
        override val original: T,
        override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf(),
        override var color: Color = Color.WHITE,
        override var copy: T? = null) : ComplexVertex<T>, VertexImpl<T, String>()