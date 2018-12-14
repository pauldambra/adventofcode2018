package com.dambra.adventofcode2018.day11

class Grid(private val gridSerial: Int) {

    //ugh topleft to gridsize to power
    private val powersAt: MutableMap<Cell, MutableMap<Int, Int>> = mutableMapOf()

    fun seekLargestThreeByThreeSquare(): Triple<Int, Cell, Int> {
        return seekLargestCellSquareOfAnySize(3..3)
    }

    fun seekLargestCellSquareOfAnySize(gridSizes: IntRange = 1..300): Triple<Int, Cell, Int> {
        val x = gridSizes.map { gridSize ->
            gridSize to largestPowerLevelAtGridSize(gridSize)
        }
            .filterNot { it.second == null }
            .maxBy { it.second!!.second }!!

        //grid size, topLeft, power
        return Triple(x.first, x.second!!.first, x.second!!.second)
    }

    private fun largestPowerLevelAtGridSize(
        gridSize: Int
    ): Pair<Cell, Int>? {

        val gridSides = 1..300

        return gridSides
            .map { x -> gridSides.map { y -> Cell(x, y) } }
            .asSequence()
            .flatten()
            .filter { cell -> squareFitsInGrid(cell, gridSize) }
            .map { cell ->
                Pair(cell, powerForSquare(cell, gridSize))
            }
            .maxBy { it.second }
    }

    fun powerForSquare(topLeft: Cell, gridSize: Int): Int {

        if (gridSize == 1) {
            val powerAtFirstCell = powerLevelOf(Cell(topLeft.x, topLeft.y), gridSerial)
            powersAt[topLeft] = mutableMapOf()
            powersAt[topLeft]!![1] = powerAtFirstCell
            return powerAtFirstCell
        }

        // if they run one at a time there's always the one smallest below from gridSize 2 and up
        // so you only need to calculate for each x where new max y
        // and for each y where new max x

        var largestCalculated: Int = 0
        if (powersAt.containsKey(topLeft)) {
            val seenPowers = powersAt[topLeft]!!

            if (topLeft == Cell(90, 269)) println("for $topLeft we've seen $seenPowers")

            largestCalculated = seenPowers[gridSize - 1] ?: 0
        }

        var sum = largestCalculated

        if (largestCalculated == 0) {
            for (x in (topLeft.x until topLeft.x + gridSize).asSequence()) {
                for (y in (topLeft.y until topLeft.y + gridSize).asSequence()) {
                    sum += powerLevelOf(Cell(x, y), gridSerial)
                }
            }
        } else {
            if (topLeft == Cell(90, 269) && gridSize == 16) println("for gridsize: $gridSize and topLeft $topLeft starting with sum $sum")

            val maxX = topLeft.x + gridSize - 1
            val maxY = topLeft.y + gridSize - 1

            for (x in (topLeft.x until topLeft.x + gridSize).asSequence()) {
                val cell = Cell(x, maxY)
                val power = powerLevelOf(cell, gridSerial)
                if (topLeft == Cell(90, 269) && gridSize == 16) {
                    println("cell: $cell and power $power")
                }
                sum += power
            }

            for (y in (topLeft.y until topLeft.y + gridSize).asSequence()) {
                val cell = Cell(maxX, y)
                val power = powerLevelOf(cell, gridSerial)
                if (topLeft == Cell(90, 269) && gridSize == 16) {
                    println("cell: $cell and power $power")
                }
                sum += power
            }

            powersAt[topLeft]!![gridSize] = sum
        }

        if (topLeft == Cell(90, 269) && gridSize == 16) println("gridsize: $gridSize with topleft $topLeft sum is $sum")
        return sum
    }

    private fun squareFitsInGrid(cell: Cell, gridSize: Int) = cell.x + gridSize <= 301 && cell.y + gridSize <= 301
}