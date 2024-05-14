package tasklist.model

enum class DueTag(val shortName: String, val color: Color) {
    IN_TIME("I", Color.GREEN),
    TODAY("T", Color.YELLOW),
    OVERDUE("O", Color.RED),
}