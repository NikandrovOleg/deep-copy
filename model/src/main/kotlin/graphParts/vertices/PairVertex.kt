package graphParts.vertices

interface PairVertex<K: Any?, S: Any?>: LazyInitVertex<Pair<K, S>, String> {
    override fun initReplica() {
        replica = Pair(properties["first"]!!.replica, properties["second"]!!.replica) as Pair<K, S>
    }
}