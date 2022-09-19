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
