package day4

class Board(private val rows: List<List<Int>>) {
    private val rowsAndColumns: List<List<Int>> = rows
        .flatMap { r -> r.mapIndexed { index, v -> index to v } }
        .groupBy { p -> p.first }
        .map { g -> g.value.map { v -> v.second } }
        .plus(rows)

    fun matches(calledNumbers: Set<Int>): Boolean {
        return rowsAndColumns.any { rc -> calledNumbers.containsAll(rc) }
    }

    fun getScore(calledNumbers: Set<Int>, lastCalledNumber: Int): Int {
        val sumOfUnmarkedNumbers = rows.asSequence()
            .flatMap { r -> r }
            .minus(calledNumbers)
            .sum()

        return sumOfUnmarkedNumbers * lastCalledNumber
    }

    override fun toString(): String {
        val builder = StringBuilder()

        rows.forEach { r ->
            builder.appendLine(r.joinToString(" ") { c -> String.format("%2d", c) })
        }

        return builder.toString()
    }
}