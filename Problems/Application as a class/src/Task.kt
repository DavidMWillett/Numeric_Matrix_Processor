class Application(val name: String) {

    // write the run method here
    fun run(input: Array<String>) {
        println(name)
        input.forEach { println(it) }
    }
}
