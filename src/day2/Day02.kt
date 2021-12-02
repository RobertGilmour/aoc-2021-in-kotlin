package day2

import java.io.File

fun main() {
    implementation(Part1Displacement(0, 0))
    implementation(Part2Displacement(0, 0, 0))
}

fun implementation(initialDisplacement: Displacement) {
    val lines = File("src/day2", "Day02.txt").readLines()

    val directions = lines.asSequence().map { l ->
        val split = l.trim().split(" ")
        Direction(split[0], split[1].toInt())
    }

    val displacement = directions.fold(initialDisplacement) { acc, d -> acc.applyDirection(d) }

    val multiplied = displacement.horizontal * displacement.vertical

    println("Output: $multiplied")
}