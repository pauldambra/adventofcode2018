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
        val a = Acre("|")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 3
            )
        )
        assertThat(b).isEqualTo(Acre(Acre.lumberyard))
    }

    @Test
    fun `trees can stay trees`() {
        val a = Acre("|")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 2
            )
        )
        assertThat(b).isEqualTo(Acre(Acre.trees))
    }

    @Test
    fun `lumberyards can become open`() {
        val a = Acre("#")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 3
            )
        )
        assertThat(b).isEqualTo(Acre(Acre.openGround))
    }

    @Test
    fun `lumberyards can stay lumberyards`() {
        val a = Acre("#")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 1,
                Acre.trees to 1
            )
        )
        assertThat(b).isEqualTo(Acre(Acre.lumberyard))
    }

    @Test
    fun `open ground can become trees`() {
        val a = Acre(".")
        val b = a.tick(
            mapOf(
                Acre.trees to 3
            )
        )
        assertThat(b).isEqualTo(Acre(Acre.trees))
    }

    @Test
    fun `open ground can stay open`() {
        val a = Acre(".")
        val b = a.tick(
            mapOf(
                Acre.trees to 2
            )
        )
        assertThat(b).isEqualTo(Acre(Acre.openGround))
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

        assertThat(forest.resourceValue()).isEqualTo(582494)
    }

    @Test
    fun `can report puzzle input resource value after a very long time`() {

        val forest = Forest(javaClass.getResource("/day18Part1Input.txt").readText())

        forest.after(1000000000)

        assertThat(forest.resourceValue()).isEqualTo(1147)
    }
}

