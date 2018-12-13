package com.dambra.adventofcode2018.day11

class Grid(private val gridSerial: Int) {

    fun seekLargestCellSquare(): CellSquare {
        var largest: CellSquare? = null

        val gridRange = 1..300
        gridRange.forEach { x ->
            gridRange.forEach { y ->
                val current = CellSquare(Cell(x, y), gridSerial, 3)
                if (largest == null || current.power() > largest!!.power()) {
                    largest = current
                }
            }
        }

        return largest!!
    }

    fun seekLargestCellSquareOfAnySize(): CellSquare {
        var largest: CellSquare? = null
        (1..(300)).forEach {
            largest = largestPowerLevelAtGridSize(it, largest)
            println("grid: $it has power ${largest!!.power()}")
        }
        return largest!!
    }

    private fun largestPowerLevelAtGridSize(
        gridSize: Int,
        largest: CellSquare?
    ): CellSquare? {
        var foundLargest = largest
        val gridSizes = 1..(301-gridSize)
        gridSizes.forEach { x ->
            gridSizes.forEach { y ->
                if (squareFitsInGrid(x, y, gridSize)) {
                    val current = CellSquare(Cell(x, y), gridSerial, gridSize)
                    if (largest == null || current.power() > largest.power()) {
                        foundLargest = current
                    }
                }
            }
        }
        return foundLargest
    }

    private fun squareFitsInGrid(x: Int, y: Int, gridSize: Int) = x + gridSize <= 300 && y + gridSize <= 300
}

class CellSquare(
    val topLeft: Cell,
    private val gridSerial: Int,
    private val gridSize: Int
) {
    fun power() =
        (topLeft.x until topLeft.x + gridSize)
            .asSequence()
            .fold(0) { xSum, x ->
                xSum + (topLeft.y until topLeft.y + gridSize)
                    .asSequence()
                    .fold(0) { ySum, y ->
                        ySum + powerLevelOf(Cell(x, y), gridSerial)
                    }
            }

}