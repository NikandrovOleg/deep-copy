import data.*
import model.GraphImpl
import model.vertices.CollectionVertexImpl
import model.vertices.ComplexVertexImpl
import model.vertices.NullVertexImpl
import model.vertices.PrimitiveVertexImpl
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.sameInstance
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

    @Test
    fun connectWithListGraph() {
        val list = listOf("b", "a", "a", "c")
        val withListVertex = ComplexVertexImpl(WithListDataClass::class, WithListDataClass(emptyList()))
        val listVertex = CollectionVertexImpl(list::class, emptyList<String>())
        val primitiveVertex0 = PrimitiveVertexImpl(String::class, "b")
        val primitiveVertex1 = PrimitiveVertexImpl(String::class, "a")
        val primitiveVertex2 = PrimitiveVertexImpl(String::class, "a")
        val primitiveVertex3 = PrimitiveVertexImpl(String::class, "c")
        withListVertex.properties["strings"] = listVertex
        listVertex.properties[0] = primitiveVertex0
        listVertex.properties[1] = primitiveVertex1
        listVertex.properties[2] = primitiveVertex2
        listVertex.properties[3] = primitiveVertex3
        establishingConnections.connect(GraphImpl(listOf(withListVertex, listVertex, primitiveVertex0,
            primitiveVertex1, primitiveVertex2, primitiveVertex3)))
        assertThat(withListVertex.replica!!.strings, equalTo(list))
        assertThat(withListVertex.replica, equalTo(WithListDataClass(mutableListOf("b", "a", "a", "c"))))
    }

    @Test
    fun connectWithMutableListGraph() {
        val list = mutableListOf("b", "a", "a", "c")
        val withListVertex = ComplexVertexImpl(WithListDataClass::class, WithListDataClass(emptyList()))
        val listVertex = CollectionVertexImpl(list::class, emptyList<String>())
        val primitiveVertex0 = PrimitiveVertexImpl(String::class, "b")
        val primitiveVertex1 = PrimitiveVertexImpl(String::class, "a")
        val primitiveVertex2 = PrimitiveVertexImpl(String::class, "a")
        val primitiveVertex3 = PrimitiveVertexImpl(String::class, "c")
        withListVertex.properties["strings"] = listVertex
        listVertex.properties[0] = primitiveVertex0
        listVertex.properties[1] = primitiveVertex1
        listVertex.properties[2] = primitiveVertex2
        listVertex.properties[3] = primitiveVertex3
        establishingConnections.connect(GraphImpl(listOf(withListVertex, listVertex, primitiveVertex0,
            primitiveVertex1, primitiveVertex2, primitiveVertex3)))
        assertThat(withListVertex.replica!!.strings as MutableList<String>, equalTo(list))
    }

    @Test
    fun connectCyclicGraphWithList() {
        val secondVertexClass = SecondVertexClass()
        val firstVertexClass = FirstVertexClass(SecondVertexClass())
        val list = emptyList<FirstVertexClass>()
        val firstVertex = ComplexVertexImpl(firstVertexClass::class, firstVertexClass)
        val secondVertex = ComplexVertexImpl(secondVertexClass::class, secondVertexClass)
        val listVertex = CollectionVertexImpl(list::class, list)
        firstVertex.properties["secondVertex"] = secondVertex
        secondVertex.properties["list"] = listVertex
        listVertex.properties[0] = firstVertex
        establishingConnections.connect(GraphImpl(listOf(firstVertex, secondVertex, listVertex)))
        assertThat(firstVertex.replica!!.secondVertex, sameInstance(secondVertexClass))
        assertThat(secondVertex.replica!!.list[0], sameInstance(firstVertexClass))
    }
}