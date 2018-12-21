package com.dambra.adventofcode2018.day18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class ForestCoordinates {
    @Test
    fun `can find their surroundings`() {
        val f = Coord(7, 0)
        val surrounds = f.surroundingCoords()
        Assertions.assertThat(surrounds).isEqualTo(
            listOf(
                Coord(6, 0),
                Coord(6, -1),
                Coord(7, -1),
                Coord(8, -1),
                Coord(8, 0),
                Coord(8, 1),
                Coord(7, 1),
                Coord(6, 1)
            )
        )
    }
}