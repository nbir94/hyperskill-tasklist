package tasklist.model

enum class Priority(val shortName: String, val color: Color) {
    CRITICAL("C", Color.RED),
    HIGH("H", Color.YELLOW),
    NORMAL("N", Color.GREEN),
    LOW("L", Color.BLUE);

    companion object {
        fun fromShortName(shortName: String): Priority {
            val expected = shortName.trim().uppercase()
            return values().first { it.shortName == expected }
        }
    }
}