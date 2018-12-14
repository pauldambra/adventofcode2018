package com.dambra.adventofcode2018.day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * For grid serial number 18, the largest total 3x3 square has a top-left corner of 33,45 (with a total power of 29); these fuel cells appear in the middle of this 5x5 region:

-2  -4   4   4   4
-4   4   4   4  -5
4   3   3   4  -4
1   1   2   4  -3
-1   0   2  -5  -2

 */

internal class GridsOfCells {
    @Test
    fun `grid serial 18 has largest 3x3 cell with power 29 at 33,45`() {
        val threeByThree = Grid(18).powerForSquare(Cell(33, 45), 3)
        assertThat(threeByThree).isEqualTo(29)
    }

    @Test
    fun `grid serial 42 has largest 3x3 cell top-left is 21,61 (with a total power of 30)`() {
        val threeByThree = Grid(42).powerForSquare(Cell(21, 61), 3)
        assertThat(threeByThree).isEqualTo(30)
    }

    @Test
    fun `can seek for largest cell in grid with serial 18`() {
        val gridSerial = 18
        val largest: Triple<Int, Cell, Int> = Grid(gridSerial).seekLargestThreeByThreeSquare()
        assertThat(largest.second).isEqualTo(Cell(33, 45))
    }

    @Test
    fun `can seek for largest cell in grid with serial 42`() {
        val gridSerial = 42
        val largest: Triple<Int, Cell, Int> = Grid(gridSerial).seekLargestThreeByThreeSquare()
        assertThat(largest.second).isEqualTo(Cell(21, 61))
    }

    @Test
    fun `can seek for largest cell in grid with puzzle input serial id 7139`() {
        val gridSerial = 7139
        val largest: Triple<Int, Cell, Int> = Grid(gridSerial).seekLargestThreeByThreeSquare()
        assertThat(largest.second).isEqualTo(Cell(20, 62))
    }

    @Test
    fun `can seek for largest cell from 1 to 300 square in grid with serial 18`() {
        val gridSerial = 18
        val cellSquare = Grid(gridSerial).seekLargestCellSquareOfAnySize()
        assertThat(cellSquare.first).isEqualTo(16)
        assertThat(cellSquare.second).isEqualTo(Cell(90,269))
        assertThat(cellSquare.third).isEqualTo(113)

    }
//
//    @Test
//    fun `can seek for largest cell from 1 to 300 square in grid with serial 42`() {
//        val gridSerial = 42
//        val cellSquare = Grid(gridSerial).seekLargestCellSquareOfAnySize()
//        val largestPower = cellSquare.largestPower()
//        assertThat(largestPower.first).isEqualTo(119)
//        assertThat(largestPower.second).isEqualTo("232,251,12")
//
//    }
}
