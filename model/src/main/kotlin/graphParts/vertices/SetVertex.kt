package graphParts.vertices

interface SetVertex<K: Any?>: LazyInitVertex<Set<K>, Int> {
    override fun initReplica() {
        replica = properties.values.map { it.replica }.toSet() as Set<K>
    }
}