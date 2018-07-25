package modelImpl

import graphParts.Graph
import graphParts.Vertex

class GraphImpl: Graph {
    override val vertexes: List<Vertex<*>>
        get() = emptyList()

    override fun getParents(vertex: Vertex<Any>): List<Vertex<*>> = emptyList()

    override fun getChildes(vertex: Vertex<Any>): List<Vertex<*>> = emptyList()
}