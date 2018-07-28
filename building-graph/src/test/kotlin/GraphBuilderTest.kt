import graphParts.Graph
import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

class GraphBuilderTest {

    @Test
    fun returnGraphOfObject() {
        assertThat(GraphBuilder.buildGraph(Any()), instanceOf(Graph::class.java))
    }

    @Test
    fun returnGraphOfOneString() {
        val str = "testString"
        val graph = GraphBuilder.buildGraph(str)
        assertThat(graph.vertexes.size, `is`(1))
        assertThat(graph.vertexes[0].copy as String, equalTo(str))
        assertThat(graph.vertexes[0].copy as String, not(sameInstance(str)))
    }
}