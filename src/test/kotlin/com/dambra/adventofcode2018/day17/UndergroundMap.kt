package com.dambra.adventofcode2018.day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UndergroundMap {
    @Test
    fun `can parse the example scan`() {
        val exampleInput = """
x=495, y=2..7
y=7, x=495..501
x=501, y=3..7
x=498, y=2..4
x=506, y=1..2
x=498, y=10..13
x=504, y=10..13
y=13, x=498..504
        """.trimIndent()

        val scan = UndergroundScan(exampleInput)

        assertThat(scan.print()).isEqualTo("""

......+.......
............#.
.#..#.......#.
.#..#..#......
.#..#..#......
.#.....#......
.#.....#......
.#######......
..............
..............
....#.....#...
....#.....#...
....#.....#...
....#######...
        """.trimIndent())
    }
}

data class Coordinate(val x: Int, val y: Int)
data class Vein(val xRange: IntRange, val yRange: IntRange) {

    val coordinates: List<Coordinate>

    init {
        val cs = mutableListOf<Coordinate>()
        xRange.forEach { x ->
            yRange.forEach { y ->
                cs.add(Coordinate(x, y))
            }
        }
        coordinates = cs.toList()
    }

    companion object {
        fun from(a: String, b: String) : Vein {

            var xRange: IntRange? = null
            var yRange: IntRange? = null

            val aParts = a.split("=")
            if (aParts[0] == "x") {
                xRange = singleValueVeinRange(aParts)
            } else {
                yRange = singleValueVeinRange(aParts)
            }

            val bParts = b.split("=")
            if (bParts[0] == "x") {
                xRange = twoValueVeinRange(bParts)
            } else {
                yRange = twoValueVeinRange(bParts)
            }

            println("found $xRange and $yRange")
            return Vein(xRange!!, yRange!!)
        }

        private fun twoValueVeinRange(
            bParts: List<String>
        ): IntRange? {
            val rangeParts = bParts[1]
                .split("..")
                .map { it.trim() }
                .map { it.toInt() }
            return rangeParts[0]..rangeParts[1]
        }

        private fun singleValueVeinRange(
            aParts: List<String>
        ): IntRange {
            val x = aParts[1].trim().toInt()
            return x..x
        }
    }
}

class UndergroundScan(scanResults: String) {
    val spring = Coordinate(500, 0)
    val veins: Set<Coordinate>

    init {
        veins = scanResults.split("\n")
            .map { it.split(", ") }
            .map { Vein.from(it[0], it[1])}
            .map { v -> v.coordinates }
            .flatten()
            .toSet()
    }

    fun print(): String {
        val maxX = veins.maxBy(Coordinate::x)!!.x
        val maxY = veins.maxBy(Coordinate::y)!!.y

        var s = ""
        (0..maxX).forEach { x ->
            (0..maxY).forEach { y ->
                val c = Coordinate(x, y)
                s += when {
                    c == spring -> "+"
                    veins.contains(c) -> "#"
                    else -> "."
                }
            }
            s + "\n"
        }
        return s
    }
}
