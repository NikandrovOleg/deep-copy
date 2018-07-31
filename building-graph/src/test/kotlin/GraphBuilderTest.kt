import data.PrimitiveDataClass
import graphParts.Graph
import graphParts.VertexProperty
import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

class GraphBuilderTest {

    @Test
    fun returnGraphOfObject() {
        assertThat(GraphBuilderImpl.buildGraph(Any()), instanceOf(Graph::class.java))
    }

    @Test
    fun returnGraphOfOneString() {
        val str = "testString"
        val graph = GraphBuilderImpl.buildGraph(str)
        assertThat(graph.vertexes.size, `is`(1))
        assertThat(graph.vertexes[0].copy as String, equalTo(str))
    }

    @Test
    fun returnGraphOfPrimitiveDataClass() {
        val dataClass = PrimitiveDataClass("str1", "str2")
        val graph = GraphBuilderImpl.buildGraph(dataClass)
        assertThat(graph.vertexes.size, `is`(3))
        assertThat(graph.vertexes.map { it.kClass }, hasItems(String::class, PrimitiveDataClass::class))
        assertThat(graph.vertexes.any { it.copy == "str1" }, equalTo(true))
        assertThat(graph.vertexes.any { it.copy == "str2" }, equalTo(true))
        assertThat(graph.vertexes.find { it.kClass == PrimitiveDataClass::class }?.properties?.map { it.vertex.copy as String },
                hasItems("str1", "str2"))
        assertThat(graph.vertexes.filter { it.kClass == String::class }.all { it.properties.isEmpty() }, equalTo(true))
    }
}