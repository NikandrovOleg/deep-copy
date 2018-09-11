package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.CollectionVertex
import kotlin.reflect.KClass

class CollectionVertexImpl(
    override val kClass: KClass<out Collection<*>>,
    override var replica: Collection<*>?,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : CollectionVertex