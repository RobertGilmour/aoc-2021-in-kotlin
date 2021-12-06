package day3

import java.io.File

fun main() {
    val lines = File("src/day3", "Day03.txt").readLines()

    println("---- Part 1 ----")

    val gammaString = calculateGamma(lines.asSequence())
    val gammaInteger = gammaString.toInt(2)
    val epsilonString = gammaString.map { c -> if (c == '1') '0' else '1' }.joinToString("")
    val epsilonInteger = epsilonString.toInt(2)

    println(gammaString)
    println(gammaInteger)
    println(epsilonString)
    println(epsilonInteger)
    println(gammaInteger * epsilonInteger)

    println("---- Part 2 ----")

    val oxygenGeneratorRatingString = calculateOxygenGeneratorRating(lines.asSequence())
    val oxygenGeneratorRatingInteger = oxygenGeneratorRatingString.toInt(2)
    val co2ScrubberRatingString = calculateCo2ScrubberRating(lines.asSequence())
    val co2ScrubberRatingInteger = co2ScrubberRatingString.toInt(2)

    println(oxygenGeneratorRatingString)
    println(oxygenGeneratorRatingInteger)
    println(co2ScrubberRatingString)
    println(co2ScrubberRatingInteger)
    println(oxygenGeneratorRatingInteger * co2ScrubberRatingInteger)
}

fun calculateGamma(lines: Sequence<String>): String {
    var gamma = ""

    val width = lines.maxOf { l -> l.count() }
    for (index in 0 until width) {
        gamma += mostCommonCharacter(lines, index)
    }

    return gamma
}

fun calculateOxygenGeneratorRating(lines: Sequence<String>, index: Int = 0): String {
    if (lines.count() <= 1) {
        return lines.single()
    }

    val mostCommonCharacter = mostCommonCharacter(lines, index)
    val filtered = lines.filter { l -> l[index] == mostCommonCharacter }
    return calculateOxygenGeneratorRating(filtered, index + 1)
}

fun calculateCo2ScrubberRating(lines: Sequence<String>, index: Int = 0): String {
    if (lines.count() <= 1) {
        return lines.single()
    }

    val leastCommonCharacter = leastCommonCharacter(lines, index)
    val filtered = lines.filter { l -> l[index] == leastCommonCharacter }
    return calculateCo2ScrubberRating(filtered, index + 1)
}

fun mostCommonCharacter(strings: Sequence<String>, index: Int): Char {
    val v = strings.fold(0) { acc, s -> if (s[index] == '1') acc + 1 else acc - 1 }
    return if (v >= 0) '1' else '0'
}

fun leastCommonCharacter(strings: Sequence<String>, index: Int): Char {
    val v = strings.fold(0) { acc, s -> if (s[index] == '1') acc + 1 else acc - 1 }
    return if (v < 0) '1' else '0'
}