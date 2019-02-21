import graphParts.Graph
import kotlin.reflect.full.isSubclassOf

class FillingCopies {
    @Suppress("UNCHECKED_CAST")
    fun fill(graph: Graph) {
        (graph.vertices.filter { it::class.isSubclassOf(ComplexVertex::class) } as List<ComplexVertex<Any>>).
            forEach { it.replica = it.kClass.createInstance() }
    }
}