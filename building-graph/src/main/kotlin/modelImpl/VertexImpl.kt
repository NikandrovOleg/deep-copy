package modelImpl

import graphParts.Color
import graphParts.Vertex
import graphParts.VertexProperty
import kotlin.reflect.KClass

data class VertexImpl<T : Any>(
        override val kClass: KClass<out T>,
        val original: T,
        override var copy: T? = null,
        override val properties: MutableList<VertexProperty<*>> = mutableListOf(),
        override var color: Color = Color.WHITE) : Vertex<T>