fun main(args: Array<String>) {
    (1..8).forEach { cups ->
        val allStacks = generateStacks(cups)
        val towers = allStacks.filter { !it.hasNesting() }
        val idealTowers = allStacks.filter { it.isIdealTower() }

        println("$cups: ${allStacks.size} stacks, ${towers.size} towers, ${idealTowers.size} ideal towers")
    }

    println("")
    println("Just ideal towers")
    println("")
    (1..20).forEach { cups ->
        val idealTowers = generateIdealTowers(cups)

        println("$cups: ${idealTowers.size}")
    }
}
