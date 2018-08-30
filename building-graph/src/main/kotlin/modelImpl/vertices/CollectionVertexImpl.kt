package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.CollectionVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

data class CollectionVertexImpl (
    override val kClass: KClass<out Collection<*>>,
    override val original: Collection<*>?,
    override var color: Color = Color.WHITE,
    override var replica: Collection<*>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : CollectionVertex, VertexImpl<Collection<*>, Int>()