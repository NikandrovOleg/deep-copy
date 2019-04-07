package graphParts.vertices

import graphParts.Vertex

interface InstantInitVertex<T : Any> : Vertex<T, String> {
    override fun initReplica() {
        properties.forEach {
            val javaProp = replica!!::class.java.getDeclaredField(it.key as String?)
            javaProp.isAccessible = true
            javaProp.set(replica, it.value.replica)
        }
    }
}