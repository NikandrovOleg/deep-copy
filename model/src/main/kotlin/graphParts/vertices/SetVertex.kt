package graphParts.vertices

interface SetVertex: LazyInitVertex<Set<*>, Int> {
    override fun initReplica() {
        replica = properties.values.map { it.replica }.toSet()
    }
}