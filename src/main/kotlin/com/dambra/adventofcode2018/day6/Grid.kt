package com.dambra.adventofcode2018.day6

class Grid(private val coordinates: List<Coordinate>) {
    private val grid = mutableMapOf<Coordinate, Int>()
    private val infiniteAreas = mutableSetOf<Coordinate>()

    fun findBiggestUnsafeRegionSize(): Int {
        val byX = coordinates
            .groupBy { it.x }
            .map { it.key to it.value.groupBy { v -> v.y } }
            .toMap()

        val (maxX, maxY, gridSize) = getGridSize()

        (0..gridSize).forEach { y ->
            (0..gridSize).forEach { x ->
                val deviceCoordinate = isDeviceCoordinate(byX, x, y)
                if (deviceCoordinate.isEmpty()) {
                    identifyRegion(x, y, maxX, maxY)
                } else {
                    thisIsOneOfTheDeviceCoordinates(deviceCoordinate)
                }
            }
        }
        return findSizeOfLargestFiniteArea()
    }

    private fun findSizeOfLargestFiniteArea(): Int {
        val x = grid
            .filterNot { infiniteAreas.contains(it.key) }
            .map { it.value to it.key }
            .sortedByDescending { it.first }
            .first()
        println("biggest finite area is $x")
        return x.first
    }

    private fun identifyRegion(x: Int, y: Int, maxX: Int, maxY: Int) {
        val closest = findClosest(Coordinate(100, x, y)) ?: return

        updateArea(closest)
        if (isEdgeCoordinate(x, maxX, y, maxY)) {
            infiniteAreas.add(closest)
        }
    }

    private fun thisIsOneOfTheDeviceCoordinates(deviceCoordinate: List<Coordinate>) {
        updateArea(deviceCoordinate.first())
    }

    private fun isDeviceCoordinate(
        byX: Map<Int, Map<Int, List<Coordinate>>>,
        x: Int,
        y: Int
    ): List<Coordinate> {
        val xMatches = byX.getOrDefault(x, emptyMap())
        val yMatches = xMatches.getOrDefault(y, emptyList())
        if (yMatches.size > 1) {
            throw Exception("shouldn't have more than one match: $yMatches")
        }
        return yMatches
    }

    private fun getGridSize(): Triple<Int, Int, Int> {
        val maxX = coordinates.maxBy { it.x }!!.x
        val maxY = coordinates.maxBy { it.y }!!.y
        val gridSize = Integer.max(maxX, maxY)
        return Triple(maxX, maxY, gridSize)
    }

    private fun updateArea(coordinate: Coordinate) {
        val current = grid.getOrDefault(coordinate, 0)
        grid[coordinate] = current + 1
    }

    private fun isEdgeCoordinate(x: Int, maxX: Int, y: Int, maxY: Int) =
        (x == 0 || x == maxX) || (y == 0 || y == maxY)

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
        val (_, _, gridSize) = getGridSize()

        return (0..gridSize).fold(0) { count, y ->
            count + (0..gridSize)
                .map { x -> Coordinate(0, x, y) }
                .filter { cell -> distancesToCells(cell).sumBy { it.second } < maxDistance }
                .count()
        }
    }

}