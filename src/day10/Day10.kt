package day10

import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val input = File("src/day10", "Day10.txt").readLines()
        .asSequence()

    val openClose = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>')

    val corruptScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137)

    val autoCorrectScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4)

    var scorePart1 = input.map { l ->
        val stack = Stack<Char>()

        for (c in l) {
            if (openClose.containsKey(c)) {
                stack.push(c)
            } else {
                if (stack.isNotEmpty() && openClose[stack.pop()] != c) {
                    // Line is corrupt
                    return@map c to stack
                }
            }
        }

        null to stack
    }
        .filter { l -> l.first != null }
        .map { l -> corruptScores.getOrDefault(l.first!!, 0) }
        .sumOf { s -> s }

    println(scorePart1)

    var scorePart2 = input.map { l ->
        val stack = Stack<Char>()

        for (c in l) {
            if (openClose.containsKey(c)) {
                stack.push(c)
            } else {
                if (stack.isNotEmpty() && openClose[stack.pop()] != c) {
                    // Line is corrupt
                    return@map Triple(c, stack, l)
                }
            }
        }

        Triple(null, stack, l)
    }
        .filter { l -> l.first == null }
        .filter { l -> l.second.isNotEmpty() }
        .map { l ->
            val stack = l.second
            val list = ArrayList<Char>()
            do {
                openClose[stack.pop()]?.let { list.add(it) }
            } while (stack.isNotEmpty())

            list
        }
        .map { l ->
            l.fold(0L) { acc, c -> acc * 5 + autoCorrectScores.getOrDefault(c, 0) }
        }
        .sorted()
        .toList()
        .toLongArray()
        .let { (it[it.size/2] + it[(it.size - 1)/2])/2 }

    println(scorePart2)
}
