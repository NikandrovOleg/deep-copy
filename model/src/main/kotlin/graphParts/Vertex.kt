package graphParts

import kotlin.reflect.KClass

interface Vertex<T : Any> {
    val color: Color
    val properties: List<VertexProperty>
    val copy: T?
    val kClass: KClass<T>
}