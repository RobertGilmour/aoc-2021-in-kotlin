package day2

class Part2Displacement(horizontal: Int, vertical: Int, private val aim: Int) : Displacement(horizontal, vertical) {

    override fun forward(value: Int): Displacement {
        return Part2Displacement(
            horizontal + value,
            vertical + aim * value,
            aim
        )
    }

    override fun up(value: Int): Displacement {
        return Part2Displacement(
            horizontal,
            vertical,
            aim - value
        )
    }

    override fun down(value: Int): Displacement {
        return Part2Displacement(
            horizontal,
            vertical,
            aim + value
        )
    }
}