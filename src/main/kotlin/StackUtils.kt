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

fun List<Cup>.hasNesting() = windowed(2).map { Pair(it.first(), it.last()) }.any { it.isNested() }
