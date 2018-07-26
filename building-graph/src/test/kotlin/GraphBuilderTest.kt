import graphParts.Graph
import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

class GraphBuilderTest {

    @Test
    fun returnGraphOfObject() {
        assertThat(GraphBuilder.buildGraph(Any()), instanceOf(Graph::class.java))
    }
}