package graphParts.vertices

interface ArrayVertex<K: Any?> : LazyInitVertex<Array<K>, Int> {
    val arrayType: Class<K>

    override fun initReplica() {
        replica = (properties.toSortedMap().values.map { it.replica } as java.util.List<K>).
            toArray(java.lang.reflect.Array.newInstance(arrayType, 0) as Array<K>)
    }
}