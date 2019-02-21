package model.vertices

import graphParts.Color
import graphParts.Vertex
import kotlin.reflect.KClass

class NullVertexImpl<T : Any>(
    override val kClass: KClass<out T>,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Any, Vertex<*, *>> = mutableMapOf()
) : NullVertex<T>