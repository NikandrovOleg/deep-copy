import data.*
import graphParts.Graph
import modelImpl.VertexImpl
import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import kotlin.reflect.full.memberProperties

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
        val graph = GraphBuilderImpl.buildGraph(PrimitiveDataClass("str1", "str2"))
        assertThat(graph.vertexes.size, `is`(3))
        assertThat(graph.vertexes.map { it.kClass }, hasItems(String::class, PrimitiveDataClass::class))
        assertThat(graph.vertexes.any { it.copy == "str1" }, equalTo(true))
        assertThat(graph.vertexes.any { it.copy == "str2" }, equalTo(true))
        assertThat(graph.vertexes.first { it.kClass == PrimitiveDataClass::class }.properties.map { it.vertex.copy as String },
                hasItems("str1", "str2"))
        assertThat(graph.vertexes.filter { it.kClass == String::class }.all { it.properties.isEmpty() }, equalTo(true))
    }

    @Test
    fun returnGraphOfPrimitiveDataWithManyFieldsClass() {
        val graph = GraphBuilderImpl.buildGraph(PrimitiveDataWithManyFieldsClass())
        assertThat(graph.vertexes.size, `is`(10))
        assertThat(graph.vertexes.map { it.kClass }, hasItems(
                PrimitiveDataWithManyFieldsClass::class,
                String::class,
                Double::class,
                Float::class,
                Long::class,
                Int::class,
                Short::class,
                Byte::class,
                Char::class,
                Boolean::class
        ))
        assertThat(graph.vertexes.first { it.kClass == PrimitiveDataWithManyFieldsClass::class }.properties.size, `is`(9))
        assertThat(graph.vertexes.filter { it.kClass != PrimitiveDataWithManyFieldsClass::class }.all { it.copy != null }, equalTo(true))
    }

    @Test
    fun returnGraphOfPrimitiveDataClassWithNullField() {
        val graph = GraphBuilderImpl.buildGraph(PrimitiveDataClass(null, null))
        assertThat(graph.vertexes.size, `is`(2))
        assertThat(graph.vertexes.count { it.kClass == PrimitiveDataClass::class }, `is`(1))
        assertThat(graph.vertexes.count { it.kClass == String::class && it.copy == null }, `is`(1))
    }

    @Test
    fun returnGraphOfComplexDataClassWithNullField() {
        val graph = GraphBuilderImpl.buildGraph(ComplexDataClass(ComplexDataClass()))
        assertThat(graph.vertexes.size, `is`(8))
        assertThat(graph.vertexes.filter { it.kClass == ComplexDataClass::class }.size, `is`(3))
        assertThat(graph.vertexes.filter { it.kClass == PrimitiveDataClass::class }.size, `is`(2))
        assertThat(graph.vertexes.filter { it.kClass == String::class }.size, `is`(3))
        assertThat(graph.vertexes.filter { it.kClass == ComplexDataClass::class }.flatMap { it.properties }.
                map { it.vertex }.count { it.kClass == ComplexDataClass::class }, `is`(2))
        assertThat(graph.vertexes.filter { it.kClass == ComplexDataClass::class }.flatMap { it.properties }.
                map { it.vertex }.count { it.kClass == PrimitiveDataClass::class }, `is`(2))
    }

    @Test
    fun returnGraphOfRecursiveObject() {
        val obj = RecursiveDataClass()
        obj.obj = obj
        val graph = GraphBuilderImpl.buildGraph(obj)
        assertThat(graph.vertexes.size, `is`(1))
        assertThat(graph.vertexes.all { it.kClass == RecursiveDataClass::class }, equalTo(true))
    }

    @Test
    fun returnCyclicGraph() {
        val obj1 = RecursiveDataClass()
        val obj2 = RecursiveDataClass(obj1)
        val obj3 = RecursiveDataClass(obj2)
        obj1.obj = obj3
        val graph = GraphBuilderImpl.buildGraph(obj1)
        assertThat(graph.vertexes.size, `is`(3))
        assertThat(graph.vertexes.all { it.properties.size == 1 }, equalTo(true))
    }
}