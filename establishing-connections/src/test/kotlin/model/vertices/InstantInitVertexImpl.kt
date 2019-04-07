package model.vertices

import graphParts.Color
import graphParts.Vertex
import graphParts.vertices.InstantInitVertex

data class InstantInitVertexImpl<T : Any>(
    override var replica: T?,
    override var color: Color = Color.WHITE,
    override val properties: MutableMap<String, Vertex<*, *>> = mutableMapOf()
) : InstantInitVertex<T>