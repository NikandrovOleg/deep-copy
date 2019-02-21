package graphParts.vertices

interface ArrayVertex : LazyInitVertex<Array<*>, Int> {
    override fun initReplica() {
        replica = properties.toSortedMap().values.map { it.replica }.toTypedArray()
    }
}