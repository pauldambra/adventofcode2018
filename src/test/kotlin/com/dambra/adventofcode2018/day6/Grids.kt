package com.dambra.adventofcode2018.day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Grids {
    private val puzzleInput: List<String> = javaClass.getResource("/day6Part1Input.txt")
        .readText()
        .split("\n")


    @Test
    fun `can find safe region size in the puzzle input grid`() {
        val coordinates = puzzleInput.toCoordinates()

        val grid = Grid(coordinates)
        assertThat(grid.findSafeRegion(10000)).isEqualTo(41145)
    }

    @Test
    fun `can find safe regions in the example input`() {
        val coordinates = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        ).toCoordinates()

        val grid = Grid(coordinates)
        assertThat(grid.findSafeRegion(32)).isEqualTo(16)
    }

    @Test
    fun `can process the puzzle input grid`() {
        val coordinates = puzzleInput.toCoordinates()

        val grid = Grid(coordinates)
        assertThat(grid.findBiggestUnsafeRegionSize()).isEqualTo(3933)
    }

    @Test
    fun `can process the example grid`() {
        val coordinates = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        ).toCoordinates()

        val grid = Grid(coordinates)
        assertThat(grid.findBiggestUnsafeRegionSize()).isEqualTo(17)
    }

}
