import graphParts.Vertex
import modelImpl.VertexWithOriginal
import modelImpl.vertices.*
import kotlin.reflect.full.createInstance

object VertexFactory {
    fun <T : Any> createVertexOfObject(obj: T?): VertexWithOriginal<T, *> {
        return when {
            (obj == null) -> VertexWithOriginal(ObsoleteInitVertexImpl<T>(obj), obj)
            (basicClasses.contains(obj::class)) -> VertexWithOriginal(ObsoleteInitVertexImpl(obj), obj)
            (obj is Array<*>) -> createArrayVertex(obj) as VertexWithOriginal<T, *>
            (obj is List<*>) -> VertexWithOriginal(ListVertexImpl(), obj) as VertexWithOriginal<T, *>
            (obj is Set<*>) -> VertexWithOriginal(SetVertexImpl(), obj) as VertexWithOriginal<T, *>
            (obj is Map<*, *>) -> createMapVertex(obj) as VertexWithOriginal<T, *>
            (obj is Pair<*, *>) -> VertexWithOriginal(PairVertexImpl(), obj) as VertexWithOriginal<T, *>
            else -> VertexWithOriginal(InstantInitVertexImpl(obj::class.createInstance()), obj)
        }
    }

    private fun <K: Any?> createArrayVertex(array: Array<K>): VertexWithOriginal<Array<K>, Int> {
        val arrayVertex = ArrayVertexImpl(array.javaClass.componentType)
        return VertexWithOriginal(arrayVertex as Vertex<Array<K>, Int>, array)
    }

    private fun <K: Any?, V: Any?> createMapVertex(map: Map<K, V>) = VertexWithOriginal(MapVertexImpl(), map)

    private val basicClasses = setOf(
        Double::class,
        Float::class,
        Long::class,
        Int::class,
        Short::class,
        Byte::class,
        String::class,
        Char::class,
        Boolean::class
    )
}