package graphParts.vertices

interface ListVertex<K: Any?> : LazyInitVertex<List<K>, Int> {
    override fun initReplica() {
        replica = properties.values.map { it.replica }.toList() as List<K>
    }
}