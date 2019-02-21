package modelImpl

import graphParts.Vertex
import graphParts.vertices.*
import kotlin.reflect.full.memberProperties

class VertexWithOriginal<T : Any, V : Any>(val vertex: Vertex<T, V>, val original: T?) : Vertex<T, V> by vertex {
    fun fillProperties(func: (Any?) -> VertexWithOriginal<*, *>) {
        when {
            (vertex is InstantInitVertex<T>) -> {
                for (prop in original!!::class.memberProperties) {
                    vertex.properties[prop.name] = func(original.javaClass.getDeclaredField(prop.name).also {
                        it.isAccessible = true
                    }.get(original))
                }
            }
            (vertex is ArrayVertex) -> {
                for (i in (original as Array<*>).indices) {
                    vertex.properties[i] = func(original[i])
                }
            }
            (vertex is ListVertex) -> {
                for (i in (original as List<*>).indices) {
                    vertex.properties[i] = func(original[i])
                }
            }
            (vertex is SetVertex) -> {
                for (i in (original as Set<*>).indices) {
                    vertex.properties[i] = func(original.elementAt(i))
                }
            }
            (vertex is MapVertex) -> {
                val pairList = (original as Map<*, *>).toList()
                for (i in pairList.indices) {
                    vertex.properties[i] = func(pairList[i])
                }
            }
            (vertex is PairVertex) -> {
                vertex.properties["first"] = func((original as Pair<*, *>).first)
                vertex.properties["second"] = func((original as Pair<*, *>).second)
            }
        }
    }
}