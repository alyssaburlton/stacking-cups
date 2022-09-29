fun main(args: Array<String>) {
    (1..8).forEach { cups ->
        val allStacks = generateStacks(cups)
        val towers = allStacks.filter { !it.hasNesting() }
        val idealTowers = allStacks.filter { it.isIdealTower() }

        val nonTowersThatCanBecomeTowersNextTime = allStacks.filter { it.isPotentialTowerWithOneMoreCup(cups) }

        println("$cups: ${allStacks.size} stacks, ${towers.size} towers, ${idealTowers.size} ideal towers")
        println(nonTowersThatCanBecomeTowersNextTime.size / 2)
    }

    println("")
    println("Just ideal towers, dividing by two (ignoring inverting)")
    println("")
    val sequence = (1..10).joinToString(", ") { cups ->
        val idealTowers = generateIdealTowers(cups)
        "${idealTowers.size / 2}"
    }
    println(sequence)

//    println("")
//    println("Just towers, using optimised algorithm")
//    println("")
//    (1..20).forEach { cups ->
//        val towers = countTowers(cups)
//        println("$cups: $towers towers")
//    }

    println("")
    (1..6).forEach { cups ->
        val towers = generateTowers(cups)
        val firstCupCounts = towers.groupBy { it.first() }.mapValues { it.value.size }
        val total = firstCupCounts.values.sum()
        val totalStartingWithLargest = firstCupCounts.getValue(Cup(cups, Orientation.UP)) + firstCupCounts.getValue(Cup(cups, Orientation.DOWN))

        println("$cups: $totalStartingWithLargest/$total = ${totalStartingWithLargest / total.toDouble()} $firstCupCounts")
    }
}
