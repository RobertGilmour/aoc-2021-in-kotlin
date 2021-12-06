package day4

import java.io.File
import java.lang.Exception

fun main() {
    val lines = File("src/day4", "Day04.txt").readLines()

    val numbers = lines[0].split(',').map { n -> n.toInt() }

    val boards = lines.asSequence().drop(1)
        .chunked(6)
        .map { b -> b.drop(1) }
        .map { b ->
            val rows = b.map { r -> r.trim().split(Regex("\\s+")).map { c -> c.toInt() } }
            Board(rows)
        }.toList()

    val (firstMatchingBoard, firstMatchingBoardScore) = findFirstWinningBoard(numbers, boards)
    println("First matching board:")
    println(firstMatchingBoard)
    println("Score: $firstMatchingBoardScore")
    println()

    val (lastMatchingBoard, lastMatchingBoardScore) = findLastWinningBoard(numbers, boards)
    println("Last matching board:")
    println(lastMatchingBoard)
    println("Score: $lastMatchingBoardScore")
    println()
}

private fun findFirstWinningBoard(numbers: List<Int>, boards: List<Board>): Pair<Board, Int> {
    var matchingBoard: Board?
    for ((index, number) in numbers.withIndex()) {
        if (index >= 5) {
            val calledNumbers = numbers.take(index + 1).toSet()

            matchingBoard = boards.firstOrNull() { b -> b.matches(calledNumbers) }

            if (matchingBoard != null) {
                return matchingBoard to matchingBoard.getScore(calledNumbers, number)
            }
        }
    }

    throw Exception("No matching board found")
}

fun findLastWinningBoard(numbers: List<Int>, boards: List<Board>): Pair<Board, Int> {
    var boardsToEvaluate = boards

    for ((index, number) in numbers.withIndex()) {
        if (index >= 5) {
            val calledNumbers = numbers.take(index + 1).toSet()

            if (boardsToEvaluate.count() > 1) {
                boardsToEvaluate = boardsToEvaluate.filter { b -> !b.matches(calledNumbers) }
            }
            else if (boardsToEvaluate.single().matches(calledNumbers)) {
                val matchingBoard = boardsToEvaluate.single()
                return matchingBoard to matchingBoard.getScore(calledNumbers, number)
            }
        }
    }

    throw Exception("No matching board found")
}
