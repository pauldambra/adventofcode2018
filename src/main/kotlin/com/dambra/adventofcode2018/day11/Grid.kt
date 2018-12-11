package com.dambra.adventofcode2018.day11

import kotlinx.coroutines.*

class Grid(private val gridSerial: Int) {
    fun seekLargestCellSquare(): CellSquare {
        var largest: CellSquare? = null

        val gridRange = 1..298
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

    @ExperimentalCoroutinesApi
    suspend fun seekLargestCellSquareOfAnySize(): CellSquare {
        val jobs = (300 downTo 1).map {
            GlobalScope.async {
                largestPowerLevelAtGridSize(it)
            }
        }

        jobs.awaitAll()

        return jobs
            .map { it.getCompleted() }
            .filter { it != null }
            .maxBy { it!!.power() }!!
    }

    private fun largestPowerLevelAtGridSize(gridSize: Int): CellSquare? {
        var largest: CellSquare? = null
        val gridRange = 1..300
        gridRange.forEach { x ->
            gridRange.forEach { y ->
                if (squareFitsInGrid(x, y, gridSize)) {
                    val current = CellSquare(Cell(x, y), gridSerial, gridSize)
                    if (largest == null || current.power() > largest!!.power()) {
                        largest = current
                    }
                }
            }
        }
        return largest
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