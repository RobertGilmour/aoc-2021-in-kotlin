package day8

import java.io.File

fun main() {
    val input = File("src/day8", "Day08.txt").readLines().map { l ->
        val split = l.split(" | ")
        val patterns = split[0].split(" ").map { p -> p.toSet() }
        val output = split[1].split(" ").map { p -> p.toSet() }
        patterns to output
    }

    val display = HashMap<Int, Set<Char>>()
    display[0] = setOf('a', 'b', 'c', 'e', 'f', 'g')
    display[1] = setOf('c', 'f')
    display[2] = setOf('a', 'c', 'd', 'e', 'g')
    display[3] = setOf('a', 'c', 'd', 'f', 'g')
    display[4] = setOf('b', 'c', 'd', 'f')
    display[5] = setOf('a', 'b', 'd', 'f', 'g')
    display[6] = setOf('a', 'b', 'd', 'e', 'f', 'g')
    display[7] = setOf('a', 'c', 'f')
    display[8] = setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
    display[9] = setOf('a', 'b', 'c', 'd', 'f', 'g')

    val countOfEasyDigits = input.sumOf { i -> i.second.count { c -> isEasyDigit(c.joinToString("")) } }
    println(countOfEasyDigits)

    val output = input.map { (patterns, output) ->
        val decodedPattern = decodePattern(patterns)
        val decodedOutput = decodeOutput(decodedPattern, output)
        decodedOutput
    }

    println(output)
    println(output.sum())
}

fun decodeOutput(decodedPattern: Map<Set<Char>, Int>, output: List<Set<Char>>): Int {
    return output.map(decodedPattern::get).joinToString("").toInt()
}

fun decodePattern(patterns: List<Set<Char>>): Map<Set<Char>, Int> {
    val simple = patterns
        .filter { p -> isEasyDigit(p.joinToString("")) }
        .associate { p ->
            when (p.count()) {
                2 -> 1 to p
                3 -> 7 to p
                4 -> 4 to p
                7 -> 8 to p
                else -> throw Exception("Cannot map length")
            }
        }

    val complex235 = patterns
        .filter { p -> p.count() == 5 }
        .associate { p ->
            if (p.intersect(simple[1]!!).count() == 2) {
                3 to p
            } else if (p.intersect(simple[4]!!).count() == 3) {
                5 to p
            } else {
                2 to p
            }
        }

    val complex069 = patterns
        .filter { p -> p.count() == 6 }
        .associate { p ->
            if (p.intersect(simple[1]!!).count() == 1) {
                6 to p
            } else if (p.intersect(simple[4]!!).count() == 4) {
                9 to p
            } else {
                0 to p
            }
        }

    return simple.plus(complex235).plus(complex069).map { kv -> kv.value to kv.key }.toMap()
}

fun isEasyDigit(string: String): Boolean {
    val length = string.length
    return length == 2 || length == 4 || length == 3 || length == 7
}