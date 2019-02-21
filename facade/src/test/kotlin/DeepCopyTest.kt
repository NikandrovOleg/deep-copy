
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DeepCopyTest {

    @Test
    fun checkForNull() {
        val deepCopy = DeepCopy()
        val replica = deepCopy.copy(null)
        assertThat(replica, equalTo(null as Any?))
    }

    @Test
    fun checkForNumber() {
        val deepCopy = DeepCopy()
        val replica = deepCopy.copy(42)
        assertThat(replica, equalTo(42))
    }
}