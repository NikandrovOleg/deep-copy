import kotlin.reflect.full.cast

class DeepCopy<T>(obj: T) {
    private val graphBuilder = GraphBuilder()
    val graph = graphBuilder.buildGraph<Any, Any>(obj)

    fun getCopy(obj: Any?): T {
        return if (obj == null) null else {
            val graphBuilder = GraphBuilder()
            val fillingCopies = FillingCopies()
            val establishingConnections = EstablishingConnections()
            val vert = graphBuilder.buildGraph<Any, Any>(obj).also {
                fillingCopies.fill(it)
                establishingConnections.connect(it)
            }.vertices[0]
            vert.kClass.cast(vert.replica)
        }
    }
}