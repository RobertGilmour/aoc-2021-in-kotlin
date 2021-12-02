package day2

abstract class Displacement(val horizontal: Int, val vertical: Int) {

    fun applyDirection(direction: Direction): Displacement {
        return when (direction.direction) {
            "forward" -> forward(direction.value)
            "up" -> up(direction.value)
            "down" -> down(direction.value)
            else -> this
        }
    }

    protected abstract fun down(value: Int): Displacement

    protected abstract fun up(value: Int): Displacement

    protected abstract fun forward(value: Int): Displacement
}