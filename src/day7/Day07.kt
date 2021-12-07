package day7

import java.io.File
import kotlin.math.abs

fun main() {
    val crabPositions = File("src/day7", "Day07.txt").readLines()
        .single()
        .split(",")
        .map(String::toLong)

    val maxPosition = crabPositions.maxOf { p -> p }

    val optimalHorizontalPositionPart1 = LongRange(0, maxPosition).asSequence()
        .map { p ->
            p to crabPositions.sumOf{ cp -> abs(p - cp) }
        }
        .minByOrNull { p -> p.second }

    val optimalHorizontalPositionPart2 = LongRange(0, maxPosition).asSequence()
        .map { p ->
            p to crabPositions.sumOf { cp ->
                val distance = abs(p - cp)
                val fuelUsed = distance * (distance + 1) / 2
                fuelUsed
            }
        }
        .minByOrNull { p -> p.second }

    println(optimalHorizontalPositionPart1)
    println(optimalHorizontalPositionPart2)
}