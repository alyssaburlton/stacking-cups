fun main(args: Array<String>) {
    (1..8).forEach { cups ->
        val allStacks = generateStacks(cups)
        val towers = allStacks.filter { !it.hasNesting() }
        val idealTowers = allStacks.filter { it.isIdealTower() }

        println("$cups: ${allStacks.size} stacks, ${towers.size} towers, ${idealTowers.size} ideal towers")
    }

    println("")
    println("Just ideal towers, dividing by two (ignoring inverting)")
    println("")
    val sequence = (1..20).joinToString(", ") { cups ->
        val idealTowers = generateIdealTowers(cups)
        "${idealTowers.size / 2}"
    }
    println(sequence)
}
