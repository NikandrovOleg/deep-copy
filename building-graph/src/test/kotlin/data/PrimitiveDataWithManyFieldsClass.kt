package data

data class PrimitiveDataWithManyFieldsClass(
        val str: String = "str",
        val dbl: Double = 1.0,
        val flt: Float = 1.0F,
        val lng: Long = 1L,
        val int: Int = 1,
        val shrt: Short = 1,
        val byt: Byte = 1,
        val char: Char = 'a',
        val bool: Boolean = true
)