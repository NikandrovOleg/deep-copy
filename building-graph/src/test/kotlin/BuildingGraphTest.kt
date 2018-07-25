import graphParts.Graph
import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

class BuildingGraphTest {

    @Test
    fun returnGraphOfObject() {
        assertThat(BuildingGraph.buildGraph(Any()), instanceOf(Graph::class.java))
    }
}