import graphParts.Graph
import modelImpl.GraphImpl
import modelImpl.VertexImpl

object GraphBuilder {
    fun buildGraph(obj: Any): Graph = GraphImpl(listOf(VertexImpl(obj::class, obj)))
}