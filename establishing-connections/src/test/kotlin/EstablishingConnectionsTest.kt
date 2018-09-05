import model.GraphImpl
import model.vertices.ComplexVertexImpl
import model.vertices.NullVertexImpl
import model.vertices.PrimitiveVertexImpl
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EstablishingConnectionsTest {
    private val establishingConnections = EstablishingConnections()

    @Test
    fun fillingNullVertexGraph() {
        val graph = GraphImpl(listOf(NullVertexImpl(String::class)))
        establishingConnections.dfs(graph)
    }

    @Test
    fun fillingSimpleDataClassGraph() {
        val simpleDataVertex = ComplexVertexImpl(SimpleDataClass::class, SimpleDataClass("a", 1))
        val stringVertex = PrimitiveVertexImpl(String::class, "str")
        val intVertex = PrimitiveVertexImpl(Int::class, 1)
        simpleDataVertex.properties["someString"] = stringVertex
        simpleDataVertex.properties["someInt"] = intVertex
        val graph = GraphImpl(listOf(simpleDataVertex, stringVertex, intVertex))
        establishingConnections.dfs(graph)
        assertThat(simpleDataVertex.replica!!.someInt, equalTo(1))
        assertThat(simpleDataVertex.replica!!.someString, equalTo("str"))
    }
}