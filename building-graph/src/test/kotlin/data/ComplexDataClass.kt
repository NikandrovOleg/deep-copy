package data

data class ComplexDataClass(
        var complexDataClass: ComplexDataClass? = null,
        var primitiveDataClass: PrimitiveDataClass? = PrimitiveDataClass("str1", "str2"),
        var str: String = "str"
)