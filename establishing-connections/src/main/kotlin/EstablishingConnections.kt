import graphParts.Color
import graphParts.Graph
import graphParts.Vertex

class EstablishingConnections {
    fun connect(graph : Graph) {
        graph.vertices.forEach { it.color = Color.WHITE }

        fun dfsVisit(currentVertex: Vertex<*, *>) {
            currentVertex.color = Color.GREY
            currentVertex.properties.filter { it.value.color == Color.WHITE }.
                forEach { dfsVisit(it.value) }
            currentVertex.color = Color.BLACK
            currentVertex.initReplica()
        }

        dfsVisit(graph.vertices[0])
    }
}