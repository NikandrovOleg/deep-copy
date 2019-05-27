
import data.ComplexDataClass
import data.NoDefaultConstructor
import data.RecursiveDataClass
import data.SimpleDataClass
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DeepCopyTest {
    val deepCopy = DeepCopy()

    @Test
    fun testNull() {
        val replica = deepCopy.getCopy(null)
        assertThat(replica, equalTo(null as Any?))
    }

    @Test
    fun testNumber() {
        val replica = deepCopy.getCopy(42)
        assertThat(replica, equalTo(42))
    }

    @Test
    fun testSimpleDataClass() {
        val someData = SimpleDataClass(1, "b")
        val replica = deepCopy.getCopy(someData)
        assertThat(replica, equalTo(someData))
        assertThat(replica, not(sameInstance(someData)))
    }

    @Test
    fun testComplexDataClass() {
        val complexData = ComplexDataClass(ComplexDataClass())
        val replica = deepCopy.getCopy(complexData)
        assertThat(replica, equalTo(complexData))
        assertThat(replica, not(sameInstance(complexData)))
        assertThat(replica!!.primitiveDataClass, equalTo(complexData.primitiveDataClass))
        assertThat(replica.primitiveDataClass, not(sameInstance(complexData.primitiveDataClass)))
        assertThat(replica.complexDataClass, equalTo(complexData.complexDataClass))
        assertThat(replica.complexDataClass, not(sameInstance(complexData.complexDataClass)))
        assertThat(replica.complexDataClass!!.primitiveDataClass,
            equalTo(complexData.complexDataClass!!.primitiveDataClass))
        assertThat(replica.complexDataClass!!.primitiveDataClass,
            not(sameInstance(complexData.complexDataClass!!.primitiveDataClass)))
    }

    @Test
    fun testRecursiveObject() {
        val recursiveData = RecursiveDataClass()
        recursiveData.obj = recursiveData
        val replica = deepCopy.getCopy(recursiveData)
        assertThat(replica, sameInstance(replica!!.obj))
        assertThat(recursiveData, not(sameInstance(replica)))
    }

    @Test
    fun testCyclicGraph() {
        val obj1 = RecursiveDataClass()
        val obj2 = RecursiveDataClass(obj1)
        val obj3 = RecursiveDataClass(obj2)
        obj1.obj = obj3
        val replica = deepCopy.getCopy(obj1)
        assertThat(replica, not(sameInstance(obj1)))
        assertThat(replica!!.obj, not(sameInstance(obj3)))
        assertThat(replica.obj, sameInstance(replica.obj!!.obj!!.obj!!.obj))
        assertThat(replica.obj!!.obj, not(sameInstance(obj2)))
        assertThat(replica.obj!!.obj, sameInstance(replica.obj!!.obj!!.obj!!.obj!!.obj))
    }

    @Test
    fun testArrayOfStrings() {
        val array = arrayOf("stringOne", "stringTwo")
        val replica = deepCopy.getCopy(array)
        assertThat(replica!!.contentEquals(array), `is`(true))
        assertThat(replica, not(sameInstance(array)))
        assertThat(replica, equalTo(array))
    }

    @Test
    fun testListOfInts() {
        val list = listOf(42, 69, 256)
        val replica = deepCopy.getCopy(list)
        list.forEachIndexed { i, value ->
            assertThat(replica!![i], `is`(value))
        }
        assertThat(list, not(sameInstance(replica)))
        assertThat(list, equalTo(replica))
    }

    @Test
    fun testSetOfDoubles() {
        val set = setOf(543.83, 875.723, 9863.6561)
        val replica = deepCopy.getCopy(set)
        assertThat(replica!!.containsAll(set), `is`(true))
        assertThat(replica.subtract(set), `is`(emptySet()))
        assertThat(set.size, `is`(replica.size))
        assertThat(set, not(sameInstance(replica)))
        assertThat(set, equalTo(replica))
    }

    @Test
    fun testMapOfStringsAndLongs() {
        val map = mapOf("firstValue" to 9876353L, "secondValue" to 77175232L, "thirdValue" to 23749238432L)
        val replica = deepCopy.getCopy(map)
        assertThat(map.keys.containsAll(replica!!.keys), `is`(true))
        assertThat(map.keys.subtract(replica.keys), `is`(emptySet()))
        assertThat(map.values.containsAll(replica.values), `is`(true))
        assertThat(map.values.subtract(replica.values), `is`(emptySet()))
        map.keys.forEach {
            assertThat(map[it], equalTo(replica[it]))
        }
        assertThat(map, equalTo(replica))
    }

    @Test(expected = IllegalArgumentException::class)
    fun getException() {
        val obj = NoDefaultConstructor(42)
        val replica = deepCopy.getCopy(obj)
    }
}