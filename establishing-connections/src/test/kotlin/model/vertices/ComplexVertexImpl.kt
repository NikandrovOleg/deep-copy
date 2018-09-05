package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.ComplexVertex
import kotlin.reflect.KClass

data class ComplexVertexImpl<T: Any>(
    override val kClass: KClass<out T>,
    override var replica: T? = null,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : ComplexVertex<T>