package modelImpl

import graphParts.Graph
import graphParts.Vertex

class GraphImpl<T: Any> private constructor(override val vertices: List<Vertex<*, *>>, val root: Vertex<T, *>)
    : Graph<T> {
    override fun getRootReplica(): T? = root.replica

    companion object {
        fun <T: Any> of(vertices: List<Vertex<*, *>>, root: Vertex<T, *>): GraphImpl<T> {
            return GraphImpl(vertices, root)
        }
    }
}