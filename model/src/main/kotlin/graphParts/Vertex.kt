package graphParts

import kotlin.reflect.KClass

interface Vertex<T : Any, V : Any> {
    val kClass: KClass<out T>
    var color: Color
    var replica: T?
    val properties: MutableMap<V, Vertex<*,*>>
}