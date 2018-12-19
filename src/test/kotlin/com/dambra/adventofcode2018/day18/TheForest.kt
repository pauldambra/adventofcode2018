package com.dambra.adventofcode2018.day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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

        assertThat(forest.resourceValue()).isEqualTo(174584)
    }
}

