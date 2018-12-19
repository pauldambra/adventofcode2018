package com.dambra.adventofcode2018.day18

data class ForestCoord(val x: Int, val y: Int) {
    private var surroundingCoords: List<ForestCoord>? = null
    fun surroundingCoords(): List<ForestCoord> {
        if (surroundingCoords == null) {
            surroundingCoords = listOf(
                ForestCoord(x - 1, y),
                ForestCoord(x - 1, y - 1),
                ForestCoord(x, y - 1),
                ForestCoord(x + 1, y - 1),
                ForestCoord(x + 1, y),
                ForestCoord(x + 1, y + 1),
                ForestCoord(x, y + 1),
                ForestCoord(x - 1, y + 1)
            )
        }
        return surroundingCoords!!
    }
}