package graphParts

import kotlin.reflect.KClass

interface Vertex<T : Any> {
    val kClass: KClass<out T>
    var color: Color
    val properties: List<VertexProperty<*>>
    var copy: T?
}