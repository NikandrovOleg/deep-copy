import graphParts.Color
import graphParts.Graph
import graphParts.Vertex
import graphParts.vertices.MapVertex
import graphParts.vertices.PairVertex
import kotlin.reflect.full.isSubclassOf

class EstablishingConnections {
    fun connect(graph : Graph) {
        graph.vertices.forEach { it.color = Color.WHITE }

        fun dfsVisit(currentVertex: Vertex<*, *>) {
            currentVertex.color = Color.GREY
            currentVertex.properties.filter { it.value.color == Color.WHITE }.
                forEach { dfsVisit(it.value) }
            currentVertex.color = Color.BLACK
            establishConnections(currentVertex as Vertex<Any, *>)
        }

        dfsVisit(graph.vertices[0])
    }

    private fun establishConnections(vertex: Vertex<Any, *>) {
        when {
            vertex.kClass.isSubclassOf(List::class) -> vertex.replica = vertex.properties.values.
                map { it.replica }.toList()
            vertex.kClass.isSubclassOf(Set::class) -> vertex.replica = vertex.properties.values.
                map { it.replica }.toSet()
            vertex is MapVertex -> vertex.replica = vertex.properties.values.associateBy(
                {(it.replica as Pair<*, *>).first}, {(it.replica as Pair<*, *>).second})
            vertex is PairVertex -> vertex.replica = Pair(vertex.properties["first"]!!.replica,
                vertex.properties["second"]!!.replica)
            else -> vertex.properties.forEach {
                val javaProp = vertex.kClass.java.getDeclaredField(it.key as String?)
                javaProp.isAccessible = true
                javaProp.set(vertex.replica, it.value.replica)
            }
        }
    }
}