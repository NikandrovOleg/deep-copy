class DeepCopy {
    private val graphBuilder = GraphBuilder()

    fun <T: Any> getCopy(obj: T?): T? {
        return if(obj == null) {
            null
        } else {
            graphBuilder.buildGraph(obj).also { EstablishingConnections().connect(it) }.getRootReplica()
        }
    }
}