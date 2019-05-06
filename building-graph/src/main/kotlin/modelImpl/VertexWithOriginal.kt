package modelImpl

import graphParts.Vertex
import graphParts.vertices.*
import kotlin.reflect.full.memberProperties

class VertexWithOriginal<T : Any, V : Any>(val vertex: Vertex<T, V>, val original: T?) : Vertex<T, V> by vertex {
    fun fillProperties(func: (Any?) -> VertexWithOriginal<*, *>) {
        when {
            (vertex is InstantInitVertex<T>) -> {
                original!!::class.memberProperties.forEach { prop ->
                    vertex.properties[prop.name] = func(original.javaClass.getDeclaredField(prop.name).also {
                        it.isAccessible = true
                    }.get(original))
                }
            }
            (vertex is ArrayVertex<*>) -> {
                for (i in (original as Array<*>).indices) {
                    vertex.properties[i] = func(original[i])
                }
            }
            (vertex is ListVertex<*>) -> {
                (original as List<*>).indices.forEach { i ->
                    vertex.properties[i] = func(original[i])
                }
            }
            (vertex is SetVertex<*>) -> {
                (original as Set<*>).indices.forEach { i ->
                    vertex.properties[i] = func(original.elementAt(i))
                }
            }
            (vertex is MapVertex<*, *>) -> {
                val pairList = (original as Map<*, *>).toList()
                pairList.indices.forEach { i ->
                    vertex.properties[i] = func(pairList[i])
                }
            }
            (vertex is PairVertex<*, *>) -> {
                vertex.properties["first"] = func((original as Pair<*, *>).first)
                vertex.properties["second"] = func((original as Pair<*, *>).second)
            }
        }
    }
}