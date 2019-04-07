import data.*
import model.GraphImpl
import model.vertices.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.sameInstance
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EstablishingConnectionsTest {
    private val establishingConnections = EstablishingConnections()

    @Test
    fun connectNullVertexGraph() {
        val graph = GraphImpl(listOf(ObsoleteInitVertexImpl<String>()))
        establishingConnections.connect(graph)
    }

    @Test
    fun connectSimpleDataClassGraph() {
        val simpleDataVertex = InstantInitVertexImpl(SimpleDataClass("a", 1))
        val stringVertex = ObsoleteInitVertexImpl("str")
        val intVertex = ObsoleteInitVertexImpl(2)
        simpleDataVertex.properties["someString"] = stringVertex
        simpleDataVertex.properties["someInt"] = intVertex
        val graph = GraphImpl(listOf(simpleDataVertex, stringVertex, intVertex))
        establishingConnections.connect(graph)
        assertThat(simpleDataVertex.replica!!.someInt, equalTo(2))
        assertThat(simpleDataVertex.replica!!.someString, equalTo("str"))
    }

    @Test
    fun connectRecursiveDataClassGraph() {
        val vertex = InstantInitVertexImpl(RecursiveDataClass())
        vertex.properties["obj"] = vertex
        establishingConnections.connect(GraphImpl(listOf(vertex)))
        assertThat(vertex.replica!!.obj, equalTo(vertex.replica))
    }

    @Test
    fun connectCyclicGraph() {
        val obj1 = RecursiveDataClass()
        val obj2 = RecursiveDataClass()
        val vertex1 = InstantInitVertexImpl(obj1)
        val vertex2 = InstantInitVertexImpl(obj2)
        vertex1.properties["obj"] = vertex2
        vertex2.properties["obj"] = vertex1
        establishingConnections.connect(GraphImpl(listOf(vertex1, vertex2)))
        assertThat(vertex1.replica!!.obj, equalTo(obj2))
        assertThat(vertex2.replica!!.obj, equalTo(obj1))
    }

    @Test
    fun connectWithListGraph() {
        val list = listOf("b", "a", "a", "c")
        val withListVertex = InstantInitVertexImpl(WithListDataClass(emptyList()))
        val listVertex = ListVertexImpl()
        val primitiveVertex0 = ObsoleteInitVertexImpl("b")
        val primitiveVertex1 = ObsoleteInitVertexImpl("a")
        val primitiveVertex2 = ObsoleteInitVertexImpl("a")
        val primitiveVertex3 = ObsoleteInitVertexImpl("c")
        withListVertex.properties["stringList"] = listVertex
        listVertex.properties[0] = primitiveVertex0
        listVertex.properties[1] = primitiveVertex1
        listVertex.properties[2] = primitiveVertex2
        listVertex.properties[3] = primitiveVertex3
        establishingConnections.connect(GraphImpl(listOf(withListVertex, listVertex, primitiveVertex0,
            primitiveVertex1, primitiveVertex2, primitiveVertex3)))
        assertThat(withListVertex.replica!!.stringList, equalTo(list))
        assertThat(withListVertex.replica, equalTo(WithListDataClass(listOf("b", "a", "a", "c"))))
    }

    @Test
    fun connectWithMutableListGraph() {
        val list = mutableListOf("b", "a", "a", "c")
        val withListVertex = InstantInitVertexImpl(WithListDataClass(mutableListOf()))
        val listVertex = ListVertexImpl(emptyList<String>())
        val primitiveVertex0 = ObsoleteInitVertexImpl("b")
        val primitiveVertex1 = ObsoleteInitVertexImpl("a")
        val primitiveVertex2 = ObsoleteInitVertexImpl("a")
        val primitiveVertex3 = ObsoleteInitVertexImpl("c")
        withListVertex.properties["stringList"] = listVertex
        listVertex.properties[0] = primitiveVertex0
        listVertex.properties[1] = primitiveVertex1
        listVertex.properties[2] = primitiveVertex2
        listVertex.properties[3] = primitiveVertex3
        establishingConnections.connect(GraphImpl(listOf(withListVertex, listVertex, primitiveVertex0,
            primitiveVertex1, primitiveVertex2, primitiveVertex3)))
        assertThat(withListVertex.replica!!.stringList as MutableList<String>, equalTo(list))
    }

    @Test
    fun connectCyclicGraphWithList() {
        val secondVertexClass = SecondVertexClass()
        val firstVertexClass = FirstVertexClass(SecondVertexClass())
        val list = emptyList<FirstVertexClass>()
        val firstVertex = InstantInitVertexImpl(firstVertexClass)
        val secondVertex = InstantInitVertexImpl(secondVertexClass)
        val listVertex = ListVertexImpl(list)
        firstVertex.properties["secondVertex"] = secondVertex
        secondVertex.properties["list"] = listVertex
        listVertex.properties[0] = firstVertex
        establishingConnections.connect(GraphImpl(listOf(firstVertex, secondVertex, listVertex)))
        assertThat(firstVertex.replica!!.secondVertex, sameInstance(secondVertexClass))
        assertThat(secondVertex.replica!!.list[0], sameInstance(firstVertexClass))
    }

    @Test
    fun connectWithSetGraph() {
        val set = setOf(3, 5, 7)
        val withSetVertex = InstantInitVertexImpl(WithSetDataClass(emptySet()))
        val setVertex = SetVertexImpl(emptySet<Int>())
        val primitiveVertex0 = ObsoleteInitVertexImpl(3)
        val primitiveVertex1 = ObsoleteInitVertexImpl(5)
        val primitiveVertex2 = ObsoleteInitVertexImpl(7)
        withSetVertex.properties["intSet"] = setVertex
        setVertex.properties[0] = primitiveVertex0
        setVertex.properties[1] = primitiveVertex1
        setVertex.properties[2] = primitiveVertex2
        establishingConnections.connect(GraphImpl(listOf(withSetVertex, setVertex, primitiveVertex0,
            primitiveVertex1, primitiveVertex2)))
        assertThat(withSetVertex.replica!!.intSet, equalTo(set))
        assertThat(withSetVertex.replica, equalTo(WithSetDataClass(setOf(3, 5, 7))))
    }

    @Test
    fun connectWithMutableSetGraph() {
        val set = mutableSetOf(3, 5, 7)
        val withSetVertex = InstantInitVertexImpl(WithSetDataClass(mutableSetOf()))
        val setVertex = SetVertexImpl(mutableSetOf<Int>())
        val primitiveVertex0 = ObsoleteInitVertexImpl(3)
        val primitiveVertex1 = ObsoleteInitVertexImpl(5)
        val primitiveVertex2 = ObsoleteInitVertexImpl(7)
        withSetVertex.properties["intSet"] = setVertex
        setVertex.properties[0] = primitiveVertex0
        setVertex.properties[1] = primitiveVertex1
        setVertex.properties[2] = primitiveVertex2
        establishingConnections.connect(GraphImpl(listOf(withSetVertex, setVertex, primitiveVertex0,
            primitiveVertex1, primitiveVertex2)))
        assertThat(withSetVertex.replica!!.intSet as MutableSet<Int>, equalTo(set))
        assertThat(withSetVertex.replica, equalTo(WithSetDataClass(setOf(3, 5, 7))))
    }

    @Test
    fun connectWithMapGraph() {
        val first = SimpleDataClass()
        val second = SimpleDataClass()

        val firstDataStringVertex = ObsoleteInitVertexImpl("firstString")
        val firstDataIntVertex = ObsoleteInitVertexImpl(1)
        val secondDataStringVertex = ObsoleteInitVertexImpl("secondString")
        val secondDataIntVertex = ObsoleteInitVertexImpl(2)

        val firstKeyVertex = ObsoleteInitVertexImpl("firstKey")
        val firstDataVertex = InstantInitVertexImpl(first).also {
            it.properties["someString"] = firstDataStringVertex
            it.properties["someInt"] = firstDataIntVertex
        }
        val firstPairVertex = PairVertexImpl(Pair("a", SimpleDataClass())).also {
            it.properties["first"] = firstKeyVertex
            it.properties["second"] = firstDataVertex
        }

        val secondKeyVertex = ObsoleteInitVertexImpl("secondKey")
        val secondDataVertex = InstantInitVertexImpl(second).also {
            it.properties["someString"] = secondDataStringVertex
            it.properties["someInt"] = secondDataIntVertex
        }
        val secondPairVertex = PairVertexImpl(Pair("a", SimpleDataClass())).also{
            it.properties["first"] = secondKeyVertex
            it.properties["second"] = secondDataVertex
        }

        val mapVertex = MapVertexImpl(emptyMap<String, SimpleDataClass>()).also {
            it.properties[0] = firstPairVertex
            it.properties[1] = secondPairVertex
        }

        val graph = GraphImpl(listOf(mapVertex, firstPairVertex, firstKeyVertex, firstDataVertex,
            firstDataStringVertex, firstDataIntVertex, secondPairVertex, secondKeyVertex, secondDataVertex,
            secondDataStringVertex, secondDataIntVertex))
        establishingConnections.connect(graph)
        assertThat(mapVertex.replica!!.size, equalTo(2))
        assertThat(mapVertex.replica!![firstKeyVertex.replica] as SimpleDataClass, sameInstance(first))
        assertThat(mapVertex.replica!![secondKeyVertex.replica] as SimpleDataClass, sameInstance(second))
    }

    @Test
    fun connectWithArrayGraph() {
        val first = SimpleDataClass()
        val second = SimpleDataClass()
        val third = SimpleDataClass()

        val firstStringVertex = ObsoleteInitVertexImpl("a")
        val firstIntVertex = ObsoleteInitVertexImpl(3)
        val secondStringVertex = ObsoleteInitVertexImpl("b")
        val secondIntVertex = ObsoleteInitVertexImpl(4)
        val thirdStringVertex = ObsoleteInitVertexImpl("c")
        val thirdIntVertex = ObsoleteInitVertexImpl(5)

        val firstVertex = InstantInitVertexImpl(first).also {
            it.properties["someString"] = firstStringVertex
            it.properties["someInt"] = firstIntVertex
        }
        val secondVertex = InstantInitVertexImpl(second).also {
            it.properties["someString"] = secondStringVertex
            it.properties["someInt"] = secondIntVertex
        }
        val thirdVertex = InstantInitVertexImpl(third).also {
            it.properties["someString"] = thirdStringVertex
            it.properties["someInt"] = thirdIntVertex
        }
        val arrayVertex = ArrayVertexImpl().also {
            it.properties[0] = firstVertex
            it.properties[1] = secondVertex
            it.properties[2] = thirdVertex
        }

        val graph = GraphImpl(listOf(arrayVertex, firstVertex, firstStringVertex, firstIntVertex, secondVertex,
            secondStringVertex, secondIntVertex, thirdVertex, thirdStringVertex, thirdIntVertex))
        establishingConnections.connect(graph)
        assertThat(arrayVertex.replica!!.size, equalTo(3))
        assertThat(arrayVertex.replica!![0] as SimpleDataClass, sameInstance(first))
        assertThat(arrayVertex.replica!![1] as SimpleDataClass, sameInstance(second))
        assertThat(arrayVertex.replica!![2] as SimpleDataClass, sameInstance(third))
    }
}