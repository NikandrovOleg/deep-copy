import graphParts.vertices.ComplexVertex
import model.GraphImpl
import model.vertices.ComplexVertexImpl
import model.vertices.NullVertexImpl
import model.vertices.PrimitiveVertexImpl
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.reflect.full.functions
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.isSubclassOf

class FillingCopiesTest {
    private val fillingCopies = FillingCopies()

    @Test
    fun fillingEmptyGraph() {
        val graph = GraphImpl(emptyList())
        fillingCopies.fill(graph)
        assertThat(graph.vertices.all { it.replica != null }, `is`(true))
    }

    @Test
    fun fillingGraphWithOneNullVertex() {
        val graph = GraphImpl(listOf(NullVertexImpl(String::class)))
        fillingCopies.fill(graph)
        assertThat(graph.vertices.filter { it::class.isSubclassOf(ComplexVertex::class) }.all { it.replica != null },
            `is`(true))
    }

    @Test
    fun fillingGraphWithOnePrimitiveVertex() {
        val graph = GraphImpl(listOf(PrimitiveVertexImpl(String::class, "string")))
        fillingCopies.fill(graph)
        assertThat(graph.vertices.filter { it::class.isSubclassOf(ComplexVertex::class) }.all { it.replica != null },
            `is`(true))
    }

    @Test
    fun fillingGraphWithOneComplexClass() {
        val complexVertex = ComplexVertexImpl(SimpleDataClass::class)
        val stringVertex = PrimitiveVertexImpl(String::class, "someString")
        val intVertex = PrimitiveVertexImpl(Int::class, 1)
        complexVertex.properties["someString"] = stringVertex
        complexVertex.properties["someInt"] = intVertex
        val graph = GraphImpl(listOf(complexVertex, stringVertex, intVertex))
        fillingCopies.fill(graph)
        assertThat(graph.vertices.filter { it::class.isSubclassOf(ComplexVertex::class) }.all { it.replica != null },
            `is`(true))
    }
}