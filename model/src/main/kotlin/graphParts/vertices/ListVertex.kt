package graphParts.vertices

interface ListVertex : LazyInitVertex<List<*>, Int> {
    override fun initReplica() {
        replica = properties.values.map { it.replica }.toList()
    }
}