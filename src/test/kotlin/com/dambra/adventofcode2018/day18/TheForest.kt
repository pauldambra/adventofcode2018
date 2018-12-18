package com.dambra.adventofcode2018.day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ForestCoordinates {
    @Test
    fun `can find their surroundings`() {
        val f = ForestCoord(7, 0)
        val surrounds = f.surroundingCoords()
        assertThat(surrounds).isEqualTo(
            listOf(
                ForestCoord(6, 0),
                ForestCoord(6, -1),
                ForestCoord(7, -1),
                ForestCoord(8, -1),
                ForestCoord(8, 0),
                ForestCoord(8, 1),
                ForestCoord(7, 1),
                ForestCoord(6, 1)
            )
        )
    }
}

internal class Acres {
    @Test
    fun `trees can become lumberyards`() {
        val a = Acre("|", ForestCoord(0, 0))
        val b = a.tick(
            mapOf(
                a.lumberyard to 3
            )
        )
        assertThat(b).isEqualTo(Acre(a.lumberyard, ForestCoord(0, 0)))
    }

    @Test
    fun `trees can stay trees`() {
        val a = Acre("|", ForestCoord(0, 0))
        val b = a.tick(
            mapOf(
                a.lumberyard to 2
            )
        )
        assertThat(b).isEqualTo(Acre(a.trees, ForestCoord(0, 0)))
    }

    @Test
    fun `lumberyards can become open`() {
        val a = Acre("#", ForestCoord(0, 0))
        val b = a.tick(
            mapOf(
                a.lumberyard to 3
            )
        )
        assertThat(b).isEqualTo(Acre(a.openGround, ForestCoord(0, 0)))
    }

    @Test
    fun `lumberyards can stay lumberyards`() {
        val a = Acre("#", ForestCoord(0, 0))
        val b = a.tick(
            mapOf(
                a.lumberyard to 1,
                a.trees to 1
            )
        )
        assertThat(b).isEqualTo(Acre(a.lumberyard, ForestCoord(0, 0)))
    }

    @Test
    fun `open ground can become trees`() {
        val a = Acre(".", ForestCoord(0, 0))
        val b = a.tick(
            mapOf(
                a.trees to 3
            )
        )
        assertThat(b).isEqualTo(Acre(a.trees, ForestCoord(0, 0)))
    }

    @Test
    fun `open ground can stay open`() {
        val a = Acre(".", ForestCoord(0, 0))
        val b = a.tick(
            mapOf(
                a.trees to 2
            )
        )
        assertThat(b).isEqualTo(Acre(a.openGround, ForestCoord(0, 0)))
    }
}

internal class TheForest {
    @Test
    fun `one minute can pass as expected`() {

        val example = """
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
        """.trimIndent()

        val forest = Forest(example)

        assertThat(forest.after(1)).isEqualTo(
            """
.......##.
......|###
.|..|...#.
..|#||...#
..##||.|#|
...#||||..
||...|||..
|||||.||.|
||||||||||
....||..|.
        """.trimIndent()
        )
    }

    @Test
    fun `four minutes can pass as expected`() {

        val example = """
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
        """.trimIndent()

        val forest = Forest(example)

        assertThat(forest.after(4)).isEqualTo(
            """
.....|.#..
...||||#..
.|.#||||..
..###||||#
...###||#|
|||##|||||
||||||||||
||||||||||
||||||||||
||||||||||
        """.trimIndent()
        )
    }

    @Test
    fun `ten minutes can pass as expected`() {

        val example = """
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
        """.trimIndent()

        val forest = Forest(example)

        assertThat(forest.after(10)).isEqualTo(
            """
.||##.....
||###.....
||##......
|##.....##
|##.....##
|##....##|
||##.####|
||#####|||
||||#|||||
||||||||||
""".trimIndent()
        )
    }

    @Test
    fun `can report its resource value`() {
        val example = """
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
        """.trimIndent()

        val forest = Forest(example)

        forest.after(10)

        assertThat(forest.resourceValue()).isEqualTo(1147)
    }

    @Test
    fun `can report puzzle input resource value`() {

        val forest = Forest(javaClass.getResource("/day18Part1Input.txt").readText())

        forest.after(10)

        assertThat(forest.resourceValue()).isEqualTo(1147)
    }
}

data class ForestCoord(val x: Int, val y: Int) {
    private var surroundingCoords: List<ForestCoord>? = null
    fun surroundingCoords(): List<ForestCoord> {
        if (surroundingCoords == null) {
            surroundingCoords = listOf(
                ForestCoord(x - 1, y),
                ForestCoord(x - 1, y - 1),
                ForestCoord(x, y - 1),
                ForestCoord(x + 1, y - 1),
                ForestCoord(x + 1, y),
                ForestCoord(x + 1, y + 1),
                ForestCoord(x, y + 1),
                ForestCoord(x - 1, y + 1)
            )
        }
        return surroundingCoords!!
    }
}

data class Acre(val type: String, val coord: ForestCoord) {
    val openGround = "."
    val trees = "|"
    val lumberyard = "#"

    fun tick(surroundings: Map<String, Int>): Acre {
        if (this.coord.x == 7) println("assessing $this against $surroundings")
        return when (type) {
            openGround -> if (surroundings.getOrDefault(trees, 0) >= 3) {
                this.copy(type = trees)
            } else {
                this.copy()
            }
            trees -> if (surroundings.getOrDefault(lumberyard, 0) >= 3) {
                if (this.coord.x == 7) println("making this a lumberyard")
                this.copy(type = lumberyard)
            } else {
                this.copy()
            }
            lumberyard -> if (
                surroundings.getOrDefault(lumberyard, 0) >= 1 &&
                surroundings.getOrDefault(trees, 0) >= 1
            ) {
                this.copy()
            } else {
                this.copy(type = openGround)
            }
            else -> throw Exception("there is no type of acre: $type")
        }
    }
}

class Forest(map: String) {
    private var acres: MutableMap<ForestCoord, Acre>
    private val maxX: Int
    private val maxY: Int

    fun resourceValue() : Int {
        val trees = acres
            .values
            .count { it.type == it.trees }
        val lumberyard = acres
            .values
            .count { it.type == it.lumberyard }
        return trees * lumberyard

    }

    init {
        val xs = mutableMapOf<ForestCoord, Acre>()
        map.split("\n").forEachIndexed { y, row ->
            row.split("").drop(1).dropLast(1).forEachIndexed { x, s ->
                val coord = ForestCoord(x, y)
                xs[coord] = Acre(s, coord)
            }
        }
        acres = xs
        maxX = acres.keys.maxBy { it.x }!!.x
        maxY = acres.keys.maxBy { it.y }!!.y
    }

    fun after(minutes: Int): String {

        for (minute in 1..minutes) {
            acres = acres.map { x: Map.Entry<ForestCoord, Acre> ->

                val surroundingTypes = x.key
                    .surroundingCoords()
                    .map { it -> acres.getOrElse(it) { null } }
                    .filterNot { it == null }
                    .map { it!!.type }
                    .groupingBy { it }
                    .eachCount()
                x.key to x.value.tick(surroundingTypes)
            }
                .toMap()
                .toMutableMap()
        }

        return print(acres)
    }

    private fun print(forest: Map<ForestCoord, Acre>): String {
        var s = ""
        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                s += forest[ForestCoord(x, y)]!!.type
            }
            s += "\n"
        }
        return s.trimIndent()
    }

}
