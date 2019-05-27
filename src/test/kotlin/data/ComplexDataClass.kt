package data

data class ComplexDataClass(
    var complexDataClass: ComplexDataClass? = null,
    var primitiveDataClass: SimpleDataClass? = SimpleDataClass(1, "str2"),
    var str: String = "str"
)