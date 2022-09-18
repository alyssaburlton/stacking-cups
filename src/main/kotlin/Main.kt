fun main(args: Array<String>) {
    val allThreeStacks = generateStacks(3)
    val allThreeTowers = allThreeStacks.filter { !it.hasNesting() }

    print(allThreeTowers.size) // 20
}
