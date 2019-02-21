package graphParts.vertices

interface MapVertex : LazyInitVertex<Map<*, *>, Int> {
    override fun initReplica() {
        replica = properties.mapValues { (it.value as PairVertex).replica }
    }
}