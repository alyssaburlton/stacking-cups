import kotlin.math.abs

fun Pair<Cup, Cup>.isNested() =
    when (first.orientation) {
        Orientation.DOWN -> when (second.orientation) {
            Orientation.UP -> false
            Orientation.DOWN -> second.size > first.size
        }
        Orientation.UP -> when (second.orientation) {
            Orientation.UP -> first.size > second.size
            Orientation.DOWN -> abs(first.size - second.size) > 1
        }
    }

fun Pair<Cup, Cup>.isLocked() =
    abs(first.size - second.size) == 1 && !isNested()

fun List<Cup>.isIdealTower() = windowed(2).all { Pair(it.first(), it.last()).isLocked() }

fun List<Cup>.hasNesting() = windowed(2).any { Pair(it.first(), it.last()).isNested() }

// This isn't really accurate, because e.g. u3 n1 n2 would count as two nested pairs. But in reality, n2 is locked on top of u3 and doesn't really nest over n1.
private fun List<Cup>.countNestedPairs() = windowed(2).count { Pair(it.first(), it.last()).isNested() }

fun List<Cup>.isPotentialTowerWithOneMoreCup(cups: Int) = countNestedPairs() == 1 && windowed(2).all {
    val pair = Pair(it.first(), it.last())
    return !pair.isNested() || pair.first.size == cups || pair.second.size == cups
}

fun String.toStack() = split(" ").map(::Cup)

fun List<Cup>.toStackString() = joinToString(" ") { "$it" }

fun List<Cup>.invert() = reversed().map { it.flip() }

fun generateStacks(cups: Int): List<List<Cup>> {
    if (cups == 1) {
        return listOf(listOf(Cup(1, Orientation.UP)), listOf(Cup(1, Orientation.DOWN)))
    }

    val previousStacks = generateStacks(cups - 1)
    val upStacks = generateStacksByAddingCup(previousStacks, Cup(cups, Orientation.UP))
    val downStacks = generateStacksByAddingCup(previousStacks, Cup(cups, Orientation.DOWN))
    return upStacks + downStacks
}
private fun generateStacksByAddingCup(previousStacks: List<List<Cup>>, cup: Cup) =
    previousStacks.flatMap { stack ->
        (0..stack.size).map { index ->
            stack.subList(0, index) + cup + stack.subList(index, stack.size)
        }
    }

/**
 * More optimised version that just generates ideal towers, using the assumption that you can only build them from
 * ideal towers from the previous dimension
 */
fun generateIdealTowers(cups: Int): List<List<Cup>> {
    if (cups == 1) {
        // All 1-cup stacks are ideal towers
        return generateStacks(1)
    }

    val previousStacks = generateIdealTowers(cups - 1)
    val upStacks = generateStacksByAddingCup(previousStacks, Cup(cups, Orientation.UP))
    val downStacks = generateStacksByAddingCup(previousStacks, Cup(cups, Orientation.DOWN))
    return (upStacks + downStacks).filter { it.isIdealTower() }
}

/**
 * Generating towers using the assumption that you can only build them from stacks
 * in the previous dimension where there are no nested pairs which don't involve the largest cup.
 *
 * You can't just include towers, because here is an example of a stack with nesting becoming a tower in the next iteration:
 *
 * u1 n2 n4 u3 -> u1 n2 u5 n4 u3
 *
 * Turns out even this optimisation does not work. For example, this gets thrown away:
 *
 * u1 n2 n3 u4
 *
 * But after two iterations, it can become this:
 *
 * u1 n2 u5 n6 n3 u4
 */
// fun generateTowersBad(cups: Int): List<List<Cup>> {
//    return generatePotentialTowersBad(cups).filter { !it.hasNesting() }
// }
// private fun generatePotentialTowersBad(cups: Int): List<List<Cup>> {
//    if (cups == 1) {
//        // All 1-cup stacks are towers
//        return generateStacks(1)
//    }
//
//    val previousStacks = generatePotentialTowersBad(cups - 1)
//    val upStacks = generateStacksByAddingCup(previousStacks, Cup(cups, Orientation.UP))
//    val downStacks = generateStacksByAddingCup(previousStacks, Cup(cups, Orientation.DOWN))
//    return (upStacks + downStacks).filter { it.canPotentiallyProduceTower(cups) }
// }
// private fun List<Cup>.canPotentiallyProduceTower(cups: Int) = windowed(2).none {
//    Pair(it.first(), it.last()).isNested() && it.first().size != cups &&
//            it.last().size != cups
// }

/**
 * Assembles towers from the base up, ignoring/discarding any instances of nesting as we go
 */
fun generateTowers(cups: Int): List<List<Cup>> {
    val cupSizes = (1..cups).toList()
    return cupSizes.flatMap { cupSize ->
        val remainingCupSizes = cupSizes.filter { it != cupSize }
        addAllPossibleCups(listOf(listOf(Cup(cupSize, Orientation.UP)), listOf(Cup(cupSize, Orientation.DOWN))), remainingCupSizes)
    }
}
fun countTowers(cups: Int): Int {
    val cupSizes = (1..cups).toList()
    return cupSizes.sumOf { cupSize ->
        val remainingCupSizes = cupSizes.filter { it != cupSize }
        addAllPossibleCups(listOf(listOf(Cup(cupSize, Orientation.UP)), listOf(Cup(cupSize, Orientation.DOWN))), remainingCupSizes).size
    }
}
private fun addAllPossibleCups(towersSoFar: List<List<Cup>>, remainingCupSizes: List<Int>): List<List<Cup>> {
    if (towersSoFar.isEmpty() || remainingCupSizes.isEmpty()) {
        return towersSoFar
    }

    return remainingCupSizes.flatMap { cupSize ->
        val upStacks = towersSoFar.map { it + Cup(cupSize, Orientation.UP) }
        val downStacks = towersSoFar.map { it + Cup(cupSize, Orientation.DOWN) }
        val stackSize = upStacks.first().size
        val nextTowers = (upStacks + downStacks).filter { !Pair(it[stackSize - 2], it[stackSize - 1]).isNested() }
        val nextCupSizes = remainingCupSizes.filter { it != cupSize }

        addAllPossibleCups(nextTowers, nextCupSizes)
    }
}
