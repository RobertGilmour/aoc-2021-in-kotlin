package day9

import java.io.File

fun main() {
    val input = File("src/day9", "Day09.txt").readLines()
        .map { l -> l.map { c -> c.digitToInt() }.toIntArray() }
        .toTypedArray()

    val outputPart1 = getLowPoints(input)
        .sumOf { p -> p.height + 1 }

    println(outputPart1)

    val outputPart2 = getLowPoints(input)
        .map { lp -> getBasinAroundLowPoint(lp, input) }
        .map { b -> b.size }
        .sortedDescending()
        .take(3)
        .reduce { acc, v -> acc * v }

    println(outputPart2)
}

private fun getBasinAroundLowPoint(
    lp: HeightPoint,
    input: Array<IntArray>
): Set<HeightPoint> {
    val pointsInBasin = HashSet<HeightPoint>() as MutableSet<HeightPoint>
    var pointsToConsider = setOf(lp)
    do {

        pointsInBasin.addAll(pointsToConsider)

        pointsToConsider = pointsToConsider.flatMap { p ->
            getAdjacentPoints(input, p).filter { ap -> ap.height < 9 }
        }.toSet().subtract(pointsToConsider).subtract(pointsInBasin)

    } while (pointsToConsider.isNotEmpty())

    return pointsInBasin.toSet()
}

private fun getLowPoints(input: Array<IntArray>): Sequence<HeightPoint> {
    return input.asSequence()
        .flatMapIndexed { y, row ->
        row.mapIndexed { x, cell ->
            val point = HeightPoint(x, y, cell)
            point to getAdjacentPoints(input, point)
        }
    }
    .filter { p -> p.first.height < p.second.minOf { ac -> ac.height } }
    .map { p -> p.first }
}

fun toHeightPointOrNull(array2d: Array<IntArray>, x: Int, y: Int): HeightPoint? {
    val height = array2d.getOrNull(y)?.getOrNull(x) ?: return null
    return HeightPoint(x, y, height)
}

fun getAdjacentPoints(array2d: Array<IntArray>, point: HeightPoint): Set<HeightPoint> {
    val x = point.x
    val y = point.y

    return setOfNotNull(
        toHeightPointOrNull(array2d, x, y - 1),
        toHeightPointOrNull(array2d, x - 1, y),
        toHeightPointOrNull(array2d, x, y + 1),
        toHeightPointOrNull(array2d, x + 1, y)
    )
}