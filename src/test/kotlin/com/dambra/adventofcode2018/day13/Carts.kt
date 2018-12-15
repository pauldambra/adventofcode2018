package com.dambra.adventofcode2018.day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Carts {

    @Test
    fun `2 carts on a vertical track`() {
        val track = """
|
v
|
|
|
^
|
""".trimIndent()

        val mine = Mine(track)

        assertThat(mine.carts).isEqualTo(
            listOf(
                Cart("v", Location(0, 1)),
                Cart("^", Location(0, 5))
            )
        )

        assertThat(mine.rails).isEqualTo(
            mapOf(
                Location(0, 0) to "|",
                Location(0, 1) to "|",
                Location(0, 2) to "|",
                Location(0, 3) to "|",
                Location(0, 4) to "|",
                Location(0, 5) to "|",
                Location(0, 6) to "|"
            )
        )

        val location = mine.crashLocation()

        assertThat(location).isEqualTo(Location(0, 3))
    }

    @Test
    fun `2 carts on a horizontal track`() {
        val track = """->---<-""".trimIndent()

        val mine = Mine(track)

        assertThat(mine.carts).isEqualTo(
            listOf(
                Cart(">", Location(1, 0)),
                Cart("<", Location(5, 0))
            )
        )

        val location = mine.crashLocation()

        assertThat(location).isEqualTo(Location(3, 0))
    }

    @Test
    fun `2 carts on a more complex track`() {
        val track = """
/->-\
|   |  /----\
| /-+--+-\  |
| | |  | v  |
\-+-/  \-+--/
  \------/
  """.trimIndent()

        val mine = Mine(track)
        val location = mine.crashLocation()

        assertThat(location).isEqualTo(Location(7, 3))
    }

    @Test
    fun `find collision in the puzzle input`() {
        val puzzleInput: String = javaClass.getResource("/day13Part1Input.txt").readText()
        val mine = Mine(puzzleInput)
        val location = mine.crashLocation()
        assertThat(location).isEqualTo(Location(103, 85))
    }
}

