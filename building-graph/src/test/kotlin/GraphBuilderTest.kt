import data.*
import graphParts.Graph
import modelImpl.vertices.ComplexVertexImpl
import modelImpl.vertices.NullVertexImpl
import modelImpl.vertices.PrimitiveVertexImpl
import org.junit.Test
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import kotlin.reflect.full.isSubclassOf

class GraphBuilderTest {

    @Test
    fun returnGraphOfObject() {
        assertThat(GraphBuilderImpl.buildGraph(Any()), instanceOf(Graph::class.java))
    }

    @Test
    fun returnGraphOfOneString() {
        val str = "testString"
        val graph = GraphBuilderImpl.buildGraph(str)
        assertThat(graph.vertices.size, `is`(1))
        assertThat(graph.vertices[0].replica as String, equalTo(str))
    }

    @Test
    fun returnGraphOfPrimitiveDataClass() {
        val graph = GraphBuilderImpl.buildGraph(PrimitiveDataClass("str1", "str2"))
        assertThat(graph.vertices.size, `is`(3))
        assertThat(graph.vertices.map { it.kClass }, hasItems(String::class, PrimitiveDataClass::class))
        assertThat(graph.vertices.any { it.replica == "str1" }, equalTo(true))
        assertThat(graph.vertices.any { it.replica == "str2" }, equalTo(true))
        assertThat(graph.vertices.first { it.kClass == PrimitiveDataClass::class }.properties.map { it.value.replica as String },
                hasItems("str1", "str2"))
        assertThat(graph.vertices.filter { it.kClass == String::class }.all { it.properties.isEmpty() }, equalTo(true))
    }

    @Test
    fun returnGraphOfPrimitiveDataWithManyFieldsClass() {
        val graph = GraphBuilderImpl.buildGraph(PrimitiveDataWithManyFieldsClass())
        assertThat(graph.vertices.size, `is`(10))
        assertThat(graph.vertices.map { it.kClass }, hasItems(
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
        assertThat(graph.vertices.first { it.kClass == PrimitiveDataWithManyFieldsClass::class }.properties.size, `is`(9))
        assertThat(graph.vertices.filter { it.kClass != PrimitiveDataWithManyFieldsClass::class }.all { it.replica != null }, equalTo(true))
    }

    @Test
    fun returnGraphOfPrimitiveDataClassWithNullField() {
        val graph = GraphBuilderImpl.buildGraph(PrimitiveDataClass(null, null))
        assertThat(graph.vertices.size, `is`(2))
        assertThat(graph.vertices.count { it.kClass == PrimitiveDataClass::class }, `is`(1))
        assertThat(graph.vertices.count { it.kClass == String::class && it.replica == null }, `is`(1))
    }

    @Test
    fun returnGraphOfComplexDataClassWithNullField() {
        val graph = GraphBuilderImpl.buildGraph(ComplexDataClass(ComplexDataClass()))
        assertThat(graph.vertices.size, `is`(8))
        assertThat(graph.vertices.filter { it.kClass == ComplexDataClass::class }.size, `is`(3))
        assertThat(graph.vertices.filter { it.kClass == PrimitiveDataClass::class }.size, `is`(2))
        assertThat(graph.vertices.filter { it.kClass == String::class }.size, `is`(3))
        assertThat(graph.vertices.filter { it is NullVertexImpl }.size, `is`(1))
        assertThat(graph.vertices.filter { it is PrimitiveVertexImpl }.size, `is`(3))
        assertThat(graph.vertices.filter { it is ComplexVertexImpl }.size, `is`(4))
    }

    @Test
    fun returnGraphOfRecursiveObject() {
        val obj = RecursiveDataClass()
        obj.obj = obj
        val graph = GraphBuilderImpl.buildGraph(obj)
        assertThat(graph.vertices.size, `is`(1))
        assertThat(graph.vertices.all { it.kClass == RecursiveDataClass::class }, equalTo(true))
    }

    @Test
    fun returnCyclicGraph() {
        val obj1 = RecursiveDataClass()
        val obj2 = RecursiveDataClass(obj1)
        val obj3 = RecursiveDataClass(obj2)
        obj1.obj = obj3
        val graph = GraphBuilderImpl.buildGraph(obj1)
        assertThat(graph.vertices.size, `is`(3))
        assertThat(graph.vertices.all { it.properties.size == 1 }, equalTo(true))
    }

    @Test
    fun returnGraphOfArrayOfString() {
        val graph = GraphBuilderImpl.buildGraph(arrayOf("string1", "string2", "string3", "string1"))
        assertThat(graph.vertices.size, `is`(4))
        assertThat(graph.vertices.count { it.kClass == Array<String>::class }, `is`(1))
        assertThat(graph.vertices.first { it.kClass == Array<String>::class }.properties.values.size, `is`(4))
        assertThat(graph.vertices.count { it.kClass == String::class }, `is`(3))
    }

    @Test
    fun returnGraphOfArrayOfNullableChars() {
        val graph = GraphBuilderImpl.buildGraph(arrayOf('a', null, null, null, 'e'))
        assertThat(graph.vertices.size, `is`(4))
        assertThat(graph.vertices.count { it.kClass == Array<Char>::class }, `is`(1))
        assertThat(graph.vertices.first { it.kClass == Array<Char>::class }.properties.values.size, `is`(5))
        assertThat(graph.vertices.count { it.kClass == Char::class }, `is`(3))
        assertThat(graph.vertices.count { it.kClass == Char::class && it is NullVertexImpl }, `is`(1))
    }

    @Test
    fun returnGraphOfListOfInts() {
        val graph = GraphBuilderImpl.buildGraph(listOf(1, 2, 1, 2, 1, 2))
        assertThat(graph.vertices.size, `is`(7))
        assertThat(graph.vertices.count { it.kClass.isSubclassOf(List::class) }, `is`(1))
        assertThat(graph.vertices.first { it.kClass.isSubclassOf(List::class) }.properties.values.size, `is`(6))
        assertThat(graph.vertices.count { it.kClass == Int::class }, `is`(6))
    }

    @Test
    fun returnGraphOfListOfNullableDouble() {
        val graph = GraphBuilderImpl.buildGraph(listOf(3.14, 5.67, null, null, 9.73))
        assertThat(graph.vertices.size, `is`(5))
        assertThat(graph.vertices.count { it.kClass.isSubclassOf(List::class) }, `is`(1))
        assertThat(graph.vertices.first { it.kClass.isSubclassOf(List::class) }.properties.values.size, `is`(5))
        assertThat(graph.vertices.count { it.kClass == Double::class }, `is`(3))
        assertThat(graph.vertices.count { it.kClass == Any::class && it is NullVertexImpl }, `is`(1))
    }

    @Test
    fun returnGraphOfSetOfFloats() {
        val graph = GraphBuilderImpl.buildGraph(setOf(7.87F, 9.32F, -8.41F))
        assertThat(graph.vertices.size, `is`(4))
        assertThat(graph.vertices.count { it.kClass.isSubclassOf(Set::class) }, `is`(1))
        assertThat(graph.vertices.first { it.kClass.isSubclassOf(Set::class) }.properties.values.size, `is`(3))
        assertThat(graph.vertices.count { it.kClass == Float::class }, `is`(3))
    }

    @Test
    fun returnGraphOfSetOfNullableLongs() {
        val graph = GraphBuilderImpl.buildGraph(setOf(4L, 893745L, null, 2L))
        assertThat(graph.vertices.size, `is`(5))
        assertThat(graph.vertices.count { it.kClass.isSubclassOf(Set::class) }, `is`(1))
        assertThat(graph.vertices.first { it.kClass.isSubclassOf(Set::class) }.properties.values.size, `is`(4))
        assertThat(graph.vertices.count { it.kClass == Long::class }, `is`(3))
        assertThat(graph.vertices.count { it.kClass == Any::class && it is NullVertexImpl }, `is`(1))
    }

    @Test
    fun returnGraphOfMap() {
        val graph = GraphBuilderImpl.buildGraph(mapOf("a" to 1, "b" to 2, "c" to 3))
        assertThat(graph.vertices.size, `is`(10))
        assertThat(graph.vertices.count { it.kClass.isSubclassOf(Map::class) }, `is`(1))
        assertThat(graph.vertices.count { it.kClass.isSubclassOf(Pair::class) }, `is`(3))
        assertThat(graph.vertices.count { it.kClass == String::class }, `is`(3))
        assertThat(graph.vertices.count { it.kClass == Int::class }, `is`(3))
        assertThat(graph.vertices.first { it.kClass.isSubclassOf(Map::class) }.properties.size, `is`(3))
        assertThat(graph.vertices.first { it.kClass.isSubclassOf(Map::class) }.properties.values.
            all { it.kClass.isSubclassOf(Pair::class) }, `is`(true))
        assertThat(graph.vertices.filter { it.kClass == Pair::class }.flatMap { it.properties.values }.
            all { it.kClass == String::class || it.kClass == Int::class }, `is`(true))
    }
}