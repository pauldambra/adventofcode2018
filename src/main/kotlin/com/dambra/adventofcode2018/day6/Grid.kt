package com.dambra.adventofcode2018.day6

class Grid(private val coordinates: List<Coordinate>) {
    private val grid = mutableMapOf<Coordinate, Int>()
    private val infiniteAreas = mutableSetOf<Coordinate>()

    fun findBiggestUnsafeRegionSize(): Int {
        val byX = coordinates
            .groupBy { it.x }
            .map { it.key to it.value.groupBy { v -> v.y } }
            .toMap()
        val maxX = coordinates.maxBy { it.x }!!.x
        val maxY = coordinates.maxBy { it.y }!!.y
        val gridSize = Integer.max(maxX, maxY)

        for (y in 0..gridSize) {

            for (x in 0..gridSize) {
                val xMatches = byX.getOrDefault(x, emptyMap())
                val yMatches = xMatches.getOrDefault(y, emptyList())
                if (yMatches.size > 1) {
                    throw Exception("shouldn't have more than one match: $yMatches")
                }
                if (yMatches.isEmpty()) {
                    //this isn't one of the coordinates find closest
                    val closest = findClosest(Coordinate(100, x, y))
                    if (closest == null) {
                        //equal distance don't do anything
                    } else {
                        updateArea(closest)
                        if (isEdgeCoordinate(x, maxX, y, maxY)) {
                            infiniteAreas.add(closest)
                        }
                    }
                } else {
                    //this is one of the coordinates. just count it
                    updateArea(yMatches.first())
                }
                val cell = yMatches.firstOrNull() ?: findClosest(Coordinate(100, x, y))
                if (cell != null) {
                    if (isEdgeCoordinate(x, maxX, y, maxY)) {
                        infiniteAreas.add(cell)
                    } else {

                    }
                }
            }
        }
        val x = grid.filterNot { infiniteAreas.contains(it.key) }
            .map { it.value to it.key }
            .sortedByDescending { it.first }
            .first()
        println("biggest finite area is $x")
        return x.first
    }

    private fun updateArea(coordinate: Coordinate) {
        val current = grid.getOrDefault(coordinate, 0)
        grid[coordinate] = current + 1
    }

    private fun isEdgeCoordinate(x: Int, maxX: Int, y: Int, maxY: Int) =
        (x == 0 || x == maxX) || (y == 0 || y == maxY)

    private fun drawClosest(coordinate: Coordinate): String {
        return findClosest(coordinate)?.id?.toString() ?: "."
    }

    private fun findClosest(coordinate: Coordinate): Coordinate? {
        val distances = distancesToCells(coordinate)
        return if (distances[0].second == distances[1].second) null else distances[0].first
    }

    private fun distancesToCells(coordinate: Coordinate): List<Pair<Coordinate, Int>> {
        return coordinates
            .map { Pair(it, it.manhattanDistanceTo(coordinate)) }
            .sortedBy { it.second }
    }

    fun findSafeRegion(maxDistance: Int): Int {
        val maxX = coordinates.maxBy { it.x }!!.x
        val maxY = coordinates.maxBy { it.y }!!.y
        val gridSize = Integer.max(maxX, maxY)

        var safeCount = 0
        for (y in 0..gridSize) {
            for (x in 0..gridSize) {
                val cell = Coordinate(0, x, y)
                if (distancesToCells(cell).sumBy { it.second } < maxDistance) {
                    safeCount++
                }
            }
        }
        return safeCount
    }

}