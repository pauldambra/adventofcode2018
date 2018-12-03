package com.dambra.adventofcode2018.day3

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int) {

    val coordinates: List<Coordinate>

    init {
        val coords = mutableListOf<Coordinate>()
        (top until top + height).forEach { y ->
            (left until left + width).forEach { x ->
                coords.add(Coordinate(x, y))
            }
        }
        coordinates = coords.toList()
    }

    companion object {
        fun parse(s: String): Claim {
            val parts = s.split(" ")

            val id = parts[0].trim('#').toInt()
            val (l, r) = parts[2].trim(':').split(",").map(String::toInt)
            val (x, y) = parts[3].split("x").map(String::toInt)

            return Claim(id, l, r, x, y)
        }
    }
}