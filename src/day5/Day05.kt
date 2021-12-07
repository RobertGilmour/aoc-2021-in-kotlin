package day5

import java.io.File

fun main() {
    val orthogonalOnly = false

    val lines = File("src/day5", "Day05.txt").readLines()

    val coordinatePairs = lines.asSequence().map(::extractCoordinatePair)

    val filteredCoordinatePairs = coordinatePairs.filter { cp ->
        !orthogonalOnly || coordinatePairIsOrthogonal(cp)
    }

    val hydrothermalLines = filteredCoordinatePairs.flatMap { cp ->
        val points = ArrayList<Pair<Int, Int>>()

        points.add(cp.first)

        var point = cp.first
        while (point != cp.second) {
            if (point.first < cp.second.first) point = point.first + 1 to point.second
            if (point.first > cp.second.first) point = point.first - 1 to point.second
            if (point.second < cp.second.second) point = point.first to point.second + 1
            if (point.second > cp.second.second) point = point.first to point.second - 1

            points.add(point)
        }

        points
    }

    val hydrothermalMap = hydrothermalLines
        .fold(HashMap<Pair<Int, Int>, Int>()) { acc, p ->
            acc.putIfAbsent(p, 0)
            acc[p] = acc[p]!! + 1
            acc
        }

    val builder = StringBuilder()
    for (y in 0..hydrothermalMap.maxOf { m -> m.key.first }) {
        for (x in 0..hydrothermalMap.maxOf { m -> m.key.second } ) {
            if (hydrothermalMap.containsKey(Pair(x, y))) {
                builder.append(hydrothermalMap[Pair(x, y)])
            } else {
                builder.append(".")
            }
        }
        builder.appendLine()
    }

    println(builder.toString())

    val dangerPoints = hydrothermalMap
        .filter { m -> m.value >= 2 }
        .count()

    println(dangerPoints)
}

fun coordinatePairIsOrthogonal(cp: Pair<Pair<Int, Int>, Pair<Int, Int>>) =
    coordinatePairIsVertical(cp) || coordinatePairIsHorizontal(cp)

private fun coordinatePairIsHorizontal(cp: Pair<Pair<Int, Int>, Pair<Int, Int>>) =
    cp.first.second == cp.second.second

private fun coordinatePairIsVertical(cp: Pair<Pair<Int, Int>, Pair<Int, Int>>) =
    cp.first.first == cp.second.first

fun extractCoordinatePair(line: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val coordinatePair = line.trim()
        .split(" -> ")
        .map { cp ->
            val coordinates = cp.split(",")
            coordinates[0].toInt() to coordinates[1].toInt()
        }
        .sortedWith(compareBy({ it.first }, { it.second }))

    return coordinatePair[0] to coordinatePair[1]
}