package com.dambra.adventofcode2018.day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PowerLevels {
    /**
     * For example, to find the power level of the fuel cell at 3,5 in a grid with serial number 8:

     * The rack ID is 3 + 10 = 13.
     * The power level starts at 13 * 5 = 65.
     * Adding the serial number produces 65 + 8 = 73.
     * Multiplying by the rack ID produces 73 * 13 = 949.
     * The hundreds digit of 949 is 9.
     * Subtracting 5 produces 9 - 5 = 4.
     *
     * So, the power level of this fuel cell is `4`.
     *
     * Here are some more example power levels:
     *
     * Fuel cell at  122,79, grid serial number 57: power level -5.
     * Fuel cell at 217,196, grid serial number 39: power level  0.
     * Fuel cell at 101,153, grid serial number 71: power level  4.
     */

    @Test
    fun `cell at 3,5 in grid with serial 8 has power level 4`() {
        val powerLevel = powerLevelOf(Cell(3, 5), 8)
        assertThat(powerLevel).isEqualTo(4)
    }

    @Test
    fun `cell at 122,79 in grid with serial 57 has power level -5`() {
        val powerLevel = powerLevelOf(Cell(122, 79), 57)
        assertThat(powerLevel).isEqualTo(-5)
    }

    @Test
    fun `cell at 217,196 in grid with serial 39 has power level 0`() {
        val powerLevel = powerLevelOf(Cell(217, 196), 39)
        assertThat(powerLevel).isEqualTo(0)
    }

    @Test
    fun `cell at 101,153 in grid with serial 71 has power level 4`() {
        val powerLevel = powerLevelOf(Cell(101, 153), 71)
        assertThat(powerLevel).isEqualTo(4)
    }

    @Test
    fun `manually calculate grid size 3 at 90, 265 on grid serial 18`() {
        /**
         *         90   91   92
         *   265    3   -5   (3)
         *   266    3   -3   (-2)
         *   267   (3) (-1)  (2)
         *
         *  having calculated 2x2 sum we only need to calculate the additional
         *  cells in order to get to a 3x3 sum
         *
         */

        cellsHaveExpectedPower()

        val cellsToAdd = gridExpander(Cell(90,265), 3)

        assertThat(cellsToAdd).isEqualTo(listOf(
            Cell(90, 267),
            Cell(91, 267),
            Cell(92, 267),
            Cell(92, 265),
            Cell(92, 266),
            Cell(92, 267)
        ))

        val sumOfNewCells = cellsToAdd.map {
            powerLevelOf(it, 18)
        }.sum()

        assertThat(sumOfNewCells).isEqualTo(7)
    }

    private fun cellsHaveExpectedPower() {
        val oneOne = powerLevelOf(Cell(90, 265), 18)
        assertThat(oneOne).isEqualTo(3)

        val oneTwo = powerLevelOf(Cell(90, 266), 18)
        assertThat(oneTwo).isEqualTo(3)

        val oneThree = powerLevelOf(Cell(90, 267), 18)
        assertThat(oneThree).isEqualTo(3)

        val twoOne = powerLevelOf(Cell(91, 265), 18)
        assertThat(twoOne).isEqualTo(-5)

        val twoTwo = powerLevelOf(Cell(91, 266), 18)
        assertThat(twoTwo).isEqualTo(-3)

        val twoThree = powerLevelOf(Cell(91, 267), 18)
        assertThat(twoThree).isEqualTo(-1)

        val threeOne = powerLevelOf(Cell(92, 265), 18)
        assertThat(threeOne).isEqualTo(3)

        val threeTwo = powerLevelOf(Cell(92, 266), 18)
        assertThat(threeTwo).isEqualTo(-2)

        val threeThree = powerLevelOf(Cell(92, 267), 18)
        assertThat(threeThree).isEqualTo(2)
    }

}

