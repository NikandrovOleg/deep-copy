package modelImpl.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.EntryVertex
import modelImpl.VertexImpl
import kotlin.reflect.KClass

class EntryVertexImpl(
    override val kClass: KClass<out Map.Entry<*, *>>,
    override val original: Map.Entry<*, *>?,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf(),
    override var color: Color = Color.WHITE,
    override var replica: Map.Entry<*, *>? = null
) : EntryVertex, VertexImpl<Map.Entry<*, *>, String>()