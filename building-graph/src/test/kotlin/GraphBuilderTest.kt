import data.ComplexDataClass
import data.PrimitiveDataClass
import data.PrimitiveDataWithManyFieldsClass
import data.RecursiveDataClass
import graphParts.Graph
import modelImpl.VertexWithOriginal
import modelImpl.vertices.InstantInitVertexImpl
import modelImpl.vertices.ObsoleteInitVertexImpl
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.reflect.full.isSubclassOf

class GraphBuilderTest {

    @Test
    fun returnGraphOfObject() {
        val graphBuilder = GraphBuilder()
        assertThat(graphBuilder.buildGraph(Any()), instanceOf(Graph::class.java))
    }

    @Test
    fun returnGraphOfOneString() {
        val graphBuilder = GraphBuilder()
        val str = "testString"
        val graph = graphBuilder.buildGraph(str)
        assertThat(graph.vertices.size, `is`(1))
        assertThat(graph.vertices[0].replica as String, equalTo(str))
    }

    @Test
    fun returnGraphOfPrimitiveDataClass() {
        val graph = GraphBuilder().buildGraph(PrimitiveDataClass("str1", "str2"))
        assertThat(graph.vertices.size, `is`(3))
        assertThat(
            graph.vertices.map { (it as VertexWithOriginal).original!!::class },
            hasItems(String::class, PrimitiveDataClass::class)
        )
        assertThat(graph.vertices.any { it.replica == "str1" }, equalTo(true))
        assertThat(graph.vertices.any { it.replica == "str2" }, equalTo(true))
        assertThat(graph.vertices.first { (it as VertexWithOriginal).original!!::class == PrimitiveDataClass::class }.properties.map { it.value.replica as String },
            hasItems("str1", "str2")
        )
        assertThat(
            graph.vertices.filter { (it as VertexWithOriginal).original!!::class == String::class }.all { it.properties.isEmpty() },
            equalTo(true)
        )
    }

    @Test
    fun returnGraphOfPrimitiveDataWithManyFieldsClass() {
        val graph = GraphBuilder().buildGraph(PrimitiveDataWithManyFieldsClass())
        assertThat(graph.vertices.size, `is`(10))
        assertThat(
            graph.vertices.map { (it as VertexWithOriginal).original!!::class }, hasItems(
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
            )
        )
        assertThat(
            graph.vertices.first { (it as VertexWithOriginal).original!!::class == PrimitiveDataWithManyFieldsClass::class }.properties.size,
            `is`(9)
        )
        assertThat(
            graph.vertices.filter { (it as VertexWithOriginal).original!!::class != PrimitiveDataWithManyFieldsClass::class }.all { it.replica != null },
            equalTo(true)
        )
    }

    @Test
    fun returnGraphOfPrimitiveDataClassWithNullField() {
        val graph = GraphBuilder().buildGraph(PrimitiveDataClass(null, null))
        assertThat(graph.vertices.size, `is`(2))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original != null && it.original!!::class == PrimitiveDataClass::class },
            `is`(1)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original == null }, `is`(1))
    }

    @Test
    fun returnGraphOfComplexDataClassWithNullField() {
        val graph = GraphBuilder().buildGraph(ComplexDataClass(ComplexDataClass()))
        assertThat(graph.vertices.size, `is`(8))
        assertThat(
            graph.vertices.filter { (it as VertexWithOriginal).original != null && it.original!!::class == ComplexDataClass::class }.size,
            `is`(2)
        )
        assertThat(
            graph.vertices.filter { (it as VertexWithOriginal).original != null && it.original!!::class == PrimitiveDataClass::class }.size,
            `is`(2)
        )
        assertThat(
            graph.vertices.filter { (it as VertexWithOriginal).original != null && it.original!!::class == String::class }.size,
            `is`(3)
        )
        assertThat(graph.vertices.filter { (it as VertexWithOriginal).vertex is ObsoleteInitVertexImpl }.size, `is`(4))
        assertThat(graph.vertices.filter { (it as VertexWithOriginal).vertex is InstantInitVertexImpl }.size, `is`(4))
    }

    @Test
    fun returnGraphOfRecursiveObject() {
        val obj = RecursiveDataClass()
        obj.obj = obj
        val graph = GraphBuilder().buildGraph(obj)
        assertThat(graph.vertices.size, `is`(1))
        assertThat(
            graph.vertices.all { (it as VertexWithOriginal).original!!::class == RecursiveDataClass::class },
            equalTo(true)
        )
    }

    @Test
    fun returnCyclicGraph() {
        val obj1 = RecursiveDataClass()
        val obj2 = RecursiveDataClass(obj1)
        val obj3 = RecursiveDataClass(obj2)
        obj1.obj = obj3
        val graph = GraphBuilder().buildGraph(obj1)
        assertThat(graph.vertices.size, `is`(3))
        assertThat(graph.vertices.all { it.properties.size == 1 }, equalTo(true))
    }

    @Test
    fun returnGraphOfArrayOfString() {
        val graph = GraphBuilder().buildGraph(arrayOf("string1", "string2", "string3", "string1"))
        assertThat(graph.vertices.size, `is`(4))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original!!::class == Array<String>::class },
            `is`(1)
        )
        assertThat(
            graph.vertices.first { (it as VertexWithOriginal).original!!::class == Array<String>::class }.properties.values.size,
            `is`(4)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original!!::class == String::class }, `is`(3))
    }

    @Test
    fun returnGraphOfArrayOfNullableChars() {
        val graph = GraphBuilder().buildGraph(arrayOf('a', null, null, null, 'e'))
        assertThat(graph.vertices.size, `is`(4))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original != null && it.original!!::class == Array<Char>::class },
            `is`(1)
        )
        assertThat(
            graph.vertices.first { (it as VertexWithOriginal).original != null && it.original!!::class == Array<Char>::class }.properties.values.size,
            `is`(5)
        )
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original != null && it.original != null && it.original!!::class == Char::class },
            `is`(2)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original == null }, `is`(1))
    }

    @Test
    fun returnGraphOfListOfInts() {
        val graph = GraphBuilder().buildGraph(listOf(1, 2, 1, 2, 1, 2))
        assertThat(graph.vertices.size, `is`(3))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original!!::class.isSubclassOf(List::class) },
            `is`(1)
        )
        assertThat(
            graph.vertices.first { (it as VertexWithOriginal).original!!::class.isSubclassOf(List::class) }.properties.values.size,
            `is`(6)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original!!::class == Int::class }, `is`(2))
    }

    @Test
    fun returnGraphOfListOfNullableDouble() {
        val graph = GraphBuilder().buildGraph(listOf(3.14, 5.67, null, null, 9.73))
        assertThat(graph.vertices.size, `is`(5))
        assertThat(graph.vertices.count {
            (it as VertexWithOriginal).original != null && it.original!!::class.isSubclassOf(
                List::class
            )
        }, `is`(1))
        assertThat(graph.vertices.first {
            (it as VertexWithOriginal).original != null && it.original!!::class.isSubclassOf(
                List::class
            )
        }.properties.values.size, `is`(5))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original != null && it.original!!::class == Double::class },
            `is`(3)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original == null }, `is`(1))
    }

    @Test
    fun returnGraphOfSetOfFloats() {
        val graph = GraphBuilder().buildGraph(setOf(7.87F, 9.32F, -8.41F))
        assertThat(graph.vertices.size, `is`(4))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original!!::class.isSubclassOf(Set::class) },
            `is`(1)
        )
        assertThat(
            graph.vertices.first { (it as VertexWithOriginal).original!!::class.isSubclassOf(Set::class) }.properties.values.size,
            `is`(3)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original!!::class == Float::class }, `is`(3))
    }

    @Test
    fun returnGraphOfSetOfNullableLongs() {
        val graph = GraphBuilder().buildGraph(setOf(4L, 893745L, null, 2L))
        assertThat(graph.vertices.size, `is`(5))
        assertThat(graph.vertices.count {
            (it as VertexWithOriginal).original != null && it.original != null && it.original!!::class.isSubclassOf(
                Set::class
            )
        }, `is`(1))
        assertThat(graph.vertices.first {
            (it as VertexWithOriginal).original != null && it.original!!::class.isSubclassOf(
                Set::class
            )
        }.properties.values.size, `is`(4))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original != null && it.original!!::class == Long::class },
            `is`(3)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original == null }, `is`(1))
    }

    @Test
    fun returnGraphOfMap() {
        val graph = GraphBuilder().buildGraph(mapOf("a" to 1, "b" to 2, "c" to 3))
        assertThat(graph.vertices.size, `is`(10))
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original!!::class.isSubclassOf(Map::class) },
            `is`(1)
        )
        assertThat(
            graph.vertices.count { (it as VertexWithOriginal).original!!::class.isSubclassOf(Pair::class) },
            `is`(3)
        )
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original!!::class == String::class }, `is`(3))
        assertThat(graph.vertices.count { (it as VertexWithOriginal).original!!::class == Int::class }, `is`(3))
        assertThat(
            graph.vertices.first { (it as VertexWithOriginal).original!!::class.isSubclassOf(Map::class) }.properties.size,
            `is`(3)
        )
        assertThat(graph.vertices.first { (it as VertexWithOriginal).original!!::class.isSubclassOf(Map::class) }.properties.values.all {
            (it as VertexWithOriginal).original!!::class.isSubclassOf(
                Pair::class
            )
        }, `is`(true))
        assertThat(graph.vertices.filter { (it as VertexWithOriginal).original!!::class == Pair::class }.flatMap { it.properties.values }.all { (it as VertexWithOriginal).original!!::class == String::class || it.original!!::class == Int::class },
            `is`(true)
        )
    }
}