import java.io.InvalidObjectException
import kotlin.math.abs

fun Pair<Cup, Cup>.isNested(): Boolean {
    return when (Pair(first.orientation, second.orientation)) {
        Pair(Orientation.DOWN, Orientation.UP) -> false // Butt-wise adjacent!
        Pair(Orientation.DOWN, Orientation.DOWN) -> second.size > first.size
        Pair(Orientation.UP, Orientation.UP) -> first.size > second.size
        Pair(Orientation.UP, Orientation.DOWN) -> abs(first.size - second.size) > 1
        else -> throw InvalidObjectException("Kotlin you suck")
    }
}

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
