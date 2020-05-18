fun square(value: Int, context: Any, continuation: (Int, Any) -> Unit) {
    // TODO: provide implementation here
    val square = value * value
    continuation(square, context)
}
