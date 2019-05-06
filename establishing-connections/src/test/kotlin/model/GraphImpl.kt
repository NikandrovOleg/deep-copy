package model

import graphParts.Graph
import graphParts.Vertex

class GraphImpl<T: Any>(override val vertices: List<Vertex<*, *>>, val root: Vertex<T, *>) : Graph<T> {
    override fun getRootReplica(): T? = root.replica
}