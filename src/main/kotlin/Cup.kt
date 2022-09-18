enum class Orientation {
    DOWN,
    UP
}

data class Cup(val size: Int, val orientation: Orientation) {
    constructor(cupString: String) : this(
        cupString[1].digitToInt(),
        if (cupString[0] == 'u') Orientation.UP else Orientation.DOWN
    )

    override fun toString(): String {
        val orientationStr = if (orientation == Orientation.UP) "u" else "n"
        return "$orientationStr$size"
    }

    fun flip(): Cup {
        val newOrientation = if (orientation == Orientation.UP) Orientation.DOWN else Orientation.UP
        return copy(orientation = newOrientation)
    }
}
