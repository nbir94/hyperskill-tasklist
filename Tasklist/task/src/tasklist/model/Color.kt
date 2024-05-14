package tasklist.model

enum class Color(private val colorCode: Int) {
    RED(101),
    YELLOW(103),
    GREEN(102),
    BLUE(104),
    DEFAULT(0);

    override fun toString(): String = colorCode.toString()
}