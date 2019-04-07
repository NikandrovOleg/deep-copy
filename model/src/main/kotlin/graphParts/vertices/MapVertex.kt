package graphParts.vertices

interface MapVertex : LazyInitVertex<Map<*, *>, Int> {
    override fun initReplica() {
        replica = properties.values.map { (it as PairVertex).replica!!.first to it.replica!!.second }.toMap()
    }
}