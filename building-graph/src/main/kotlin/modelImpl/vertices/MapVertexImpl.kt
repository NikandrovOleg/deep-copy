package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.MapVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

class MapVertexImpl(
    override val kClass: KClass<out Map<*, *>>,
    override val original: Map<*, *>?,
    override var color: Color = Color.WHITE,
    override var copy: Map<*, *>? = null,
    override val properties: MutableMap<Int, Vertex<*, *>> = mutableMapOf()
) : MapVertex, VertexImpl<Map<*, *>, Int>()