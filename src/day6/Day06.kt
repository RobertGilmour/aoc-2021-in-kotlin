package day6

import java.io.File

fun main() {
    var fishAges = File("src/day6", "Day06.txt").readLines()
        .single()
        .split(",")
        .map(String::toInt)
        .groupBy { a -> a }
        .map { a -> a.key to a.value.count().toLong() }
        .toMap()

    val iterations = 256

    for (index in 1..iterations) {
        val newFishAges = HashMap<Int, Long>()
        fishAges.forEach { a ->
            if (a.key == 0) {
                newFishAges[6] = a.value.plus(newFishAges.getOrDefault(6, 0))
                newFishAges[8] = a.value
            }
            else {
                newFishAges[a.key.minus(1)] =
                    fishAges.getOrDefault(a.key, 0)
                        .plus(newFishAges.getOrDefault(a.key.minus(1), 0))
            }
        }

        fishAges = newFishAges
    }

    println(fishAges.map { kv -> kv.value }.sum())
}