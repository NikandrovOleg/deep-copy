package modelImpl

import graphParts.Graph
import graphParts.Vertex

class GraphImpl(override val vertexes: List<Vertex<*>> = emptyList()) : Graph {

    override fun getParents(vertex: Vertex<Any>): List<Vertex<*>> = emptyList()

    override fun getChildes(vertex: Vertex<Any>): List<Vertex<*>> = emptyList()
}