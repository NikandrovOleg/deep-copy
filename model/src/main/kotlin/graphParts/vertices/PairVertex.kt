package graphParts.vertices

interface PairVertex: LazyInitVertex<Pair<*, *>, String> {
    override fun initReplica() {
        replica = Pair(properties["first"]!!.replica, properties["second"]!!.replica)
    }
}