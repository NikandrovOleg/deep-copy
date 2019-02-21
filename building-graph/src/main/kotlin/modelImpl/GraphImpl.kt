package modelImpl

import graphParts.Graph
import graphParts.Vertex

class GraphImpl private constructor(override val vertices: List<Vertex<*, *>>) : Graph {

    companion object {
        fun of(vertices: List<Vertex<*, *>>): GraphImpl {
            return GraphImpl(vertices)
        }
    }
}