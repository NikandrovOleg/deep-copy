import data.RecursiveDataClass
import data.SimpleDataClass
import model.GraphImpl
import model.vertices.ComplexVertexImpl
import model.vertices.NullVertexImpl
import model.vertices.PrimitiveVertexImpl
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EstablishingConnectionsTest {
    private val establishingConnections = EstablishingConnections()

    @Test
    fun connectNullVertexGraph() {
        val graph = GraphImpl(listOf(NullVertexImpl(String::class)))
        establishingConnections.connect(graph)
    }

    @Test
    fun connectSimpleDataClassGraph() {
        val simpleDataVertex = ComplexVertexImpl(SimpleDataClass::class, SimpleDataClass("a", 1))
        val stringVertex = PrimitiveVertexImpl(String::class, "str")
        val intVertex = PrimitiveVertexImpl(Int::class, 1)
        simpleDataVertex.properties["someString"] = stringVertex
        simpleDataVertex.properties["someInt"] = intVertex
        val graph = GraphImpl(listOf(simpleDataVertex, stringVertex, intVertex))
        establishingConnections.connect(graph)
        assertThat(simpleDataVertex.replica!!.someInt, equalTo(1))
        assertThat(simpleDataVertex.replica!!.someString, equalTo("str"))
    }

    @Test
    fun connectRecursiveDataClassGraph() {
        val vertex = ComplexVertexImpl(RecursiveDataClass::class, RecursiveDataClass())
        vertex.properties["obj"] = vertex
        establishingConnections.connect(GraphImpl(listOf(vertex)))
        assertThat(vertex.replica!!.obj, equalTo(vertex.replica))
    }

    @Test
    fun connectCyclicGraph() {
        val obj1 = RecursiveDataClass()
        val obj2 = RecursiveDataClass()
        val vertex1 = ComplexVertexImpl(RecursiveDataClass::class, obj1)
        val vertex2 = ComplexVertexImpl(RecursiveDataClass::class, obj2)
        vertex1.properties["obj"] = vertex2
        vertex2.properties["obj"] = vertex1
        establishingConnections.connect(GraphImpl(listOf(vertex1, vertex2)))
        assertThat(vertex1.replica!!.obj, equalTo(obj2))
        assertThat(vertex2.replica!!.obj, equalTo(obj1))
    }
}