package com.dambra.adventofcode2018.day18

data class Coord(val x: Int, val y: Int) {
    private var surroundingCoords: List<Coord>? = null
    fun surroundingCoords(): List<Coord> {
        if (surroundingCoords == null) {
            surroundingCoords = listOf(
                Coord(x - 1, y),
                Coord(x - 1, y - 1),
                Coord(x, y - 1),
                Coord(x + 1, y - 1),
                Coord(x + 1, y),
                Coord(x + 1, y + 1),
                Coord(x, y + 1),
                Coord(x - 1, y + 1)
            )
        }
        return surroundingCoords!!
    }
}