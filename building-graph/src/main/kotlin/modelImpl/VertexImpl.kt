package modelImpl

import graphParts.Color
import graphParts.Vertex
import graphParts.VertexProperty
import kotlin.reflect.KClass

data class VertexImpl<T : Any>(
        override val kClass: KClass<out T>,
        override var copy: T?,
        override val properties: List<VertexProperty> = emptyList(),
        override var color: Color = Color.WHITE) : Vertex<T>