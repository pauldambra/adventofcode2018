package com.dambra.adventofcode2018.day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Coordinates {

    @Test
    fun `can label coordinates`() {
        val coordinates = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        ).toCoordinates()

        assertThat(coordinates.map { it.id }).containsExactly(
            0, 1, 2, 3, 4, 5
        )
    }

    @Test
    fun `can parse coordinates`() {
        val coordinates = listOf(
            "1, 6"
        ).toCoordinates()

        assertThat(coordinates).containsExactly(
            Coordinate(0, 1, 6)
        )
    }

    @Test
    fun `can calculate manhattan distance`() {
        val a = Coordinate(0, 1, 1)
        val b = Coordinate(1, 3, 3)
        assertThat(a.manhattanDistanceTo(b)).isEqualTo(4)

        val c = Coordinate(0, 0, 0)
        val d = Coordinate(1, 6, 6)
        assertThat(c.manhattanDistanceTo(d)).isEqualTo(12)
    }

}
