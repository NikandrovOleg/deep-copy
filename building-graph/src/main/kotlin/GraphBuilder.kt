import graphParts.Graph
import modelImpl.GraphImpl

object GraphBuilder {
    fun buildGraph(obj: Any): Graph {
        return GraphImpl()
    }
}