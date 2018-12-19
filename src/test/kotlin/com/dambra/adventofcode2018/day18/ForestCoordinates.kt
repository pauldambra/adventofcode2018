package com.dambra.adventofcode2018.day18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class ForestCoordinates {
    @Test
    fun `can find their surroundings`() {
        val f = ForestCoord(7, 0)
        val surrounds = f.surroundingCoords()
        Assertions.assertThat(surrounds).isEqualTo(
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