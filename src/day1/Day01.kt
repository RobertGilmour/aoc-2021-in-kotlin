package day1

import java.io.File

fun main() {
    val lines = File("src/day1", "Day01.txt").readLines()

    val readings = lines.asSequence().map(String::toInt)

    val countOfIncreasingPairs = readings.countIncreasingPairs()

    val countOfIncreasingPairsWithSlidingWindow = readings
        .sumAcrossWindow(3)
        .countIncreasingPairs()

    println("Output Part 1: $countOfIncreasingPairs")
    println("Output Part 2: $countOfIncreasingPairsWithSlidingWindow")
}

fun Sequence<Int>.countIncreasingPairs(): Int {
    return this
        .zipWithNext()
        .count { (first, second) -> first < second }
}

fun Sequence<Int>.sumAcrossWindow(windowSize: Int): Sequence<Int> {
    return this.windowed(windowSize) { it.sum() }
}