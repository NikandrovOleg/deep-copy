package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.MapVertex
import kotlin.reflect.KClass

data class MapVertexImpl(
    override val kClass: KClass<out Map<*, *>>,
    override var color: Color = Color.WHITE,
    override var replica: Map<*, *>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : MapVertex