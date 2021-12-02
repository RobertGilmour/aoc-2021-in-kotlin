package day2

class Part1Displacement(horizontal: Int, vertical: Int) : Displacement(horizontal, vertical) {

    override fun forward(value: Int): Displacement {
        return Part1Displacement(horizontal + value, vertical)
    }

    override  fun up(value: Int): Displacement {
        return Part1Displacement(horizontal, vertical - value)
    }

    override  fun down(value: Int): Displacement {
        return Part1Displacement(horizontal, vertical + value)
    }
}