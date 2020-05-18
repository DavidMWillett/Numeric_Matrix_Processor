// TODO: provide three functions here
fun identity(value: Int): Int = value

fun half(value: Int): Int = value / 2

fun zero(value: Int): Int = 0

fun generate(functionName: String): (Int) -> Int {
    // TODO: provide implementation here
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}
