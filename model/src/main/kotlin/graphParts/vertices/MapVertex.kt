package graphParts.vertices

interface MapVertex<K: Any?, S: Any?> : LazyInitVertex<Map<K, S>, Int> {
    override fun initReplica() {
        replica = properties.values.map { (it.replica as Pair<K, S>).first to (it.replica as Pair<K, S>).second }.toMap()
    }
}