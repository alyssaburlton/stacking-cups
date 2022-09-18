fun main(args: Array<String>) {
    println("Hi!")

    val list = listOf(Cup(1, Orientation.UP), Cup(2, Orientation.DOWN))
    list.hasNesting()
}
