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
        val threeByThree = ThreeByThree(Cell(33, 45), 18)
        assertThat(threeByThree.power()).isEqualTo(29)
    }

    @Test
    fun `grid serial 42 has largest 3x3 cell top-left is 21,61 (with a total power of 30)`() {
        val threeByThree = ThreeByThree(Cell(21, 61), 42)
        assertThat(threeByThree.power()).isEqualTo(30)
    }

    @Test
    fun `can seek for largest cell in grid with serial 18`() {
        val gridSerial = 18
        val largest: ThreeByThree = Grid(gridSerial).seekLargestThreeByThree()
        assertThat(largest.topLeft).isEqualTo(Cell(33, 45))
    }

    @Test
    fun `can seek for largest cell in grid with serial 42`() {
        val gridSerial = 42
        val largest: ThreeByThree = Grid(gridSerial).seekLargestThreeByThree()
        assertThat(largest.topLeft).isEqualTo(Cell(21, 61))
    }

    @Test
    fun `can seek for largest cell in grid with puzzle input serial id 7139`() {
        val gridSerial = 7139
        val largest: ThreeByThree = Grid(gridSerial).seekLargestThreeByThree()
        assertThat(largest.topLeft).isEqualTo(Cell(20, 62))
    }
}

class Grid(private val gridSerial: Int) {
    fun seekLargestThreeByThree(): ThreeByThree {
        var largest: ThreeByThree? = null

        val gridRange = 1..298
        gridRange.forEach { x ->
            gridRange.forEach { y ->
                val current = ThreeByThree(Cell(x, y), gridSerial)
                if (largest == null || current.power() > largest!!.power()) {
                    largest = current
                }
            }
        }

        return largest!!
    }
}

class ThreeByThree(val topLeft: Cell, private val gridSerial: Int) {
    fun power(): Int {
        return (topLeft.x until topLeft.x + 3).fold(0) { xSum, x ->
            xSum + (topLeft.y until topLeft.y + 3).fold(0) { ySum, y ->
                ySum + powerLevelOf(Cell(x, y), gridSerial)
            }
        }
    }

}
