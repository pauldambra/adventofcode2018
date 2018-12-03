package com.dambra.adventofcode2018.day3

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int) {

    fun contains(coordinate: Coordinate): Boolean {

        println("includes ${coordinates()}")

        val withinXBounds = minX() <= coordinate.x && coordinate.x <= maxX()
        println("within x bounds? $withinXBounds")
        val withinYBounds = minY() <= coordinate.y && coordinate.y <= maxY()
        println("within y bounds? $withinYBounds")
        return withinXBounds && withinYBounds
    }

    fun minX() = left

    fun maxX() = left + width - 1

    fun minY() = top

    fun maxY() = top + height - 1

    private val coords = mutableListOf<Coordinate>()
    fun coordinates(): List<Coordinate> {
        if (coords.isEmpty()) {
            (minY()..maxY()).forEach { y ->
                (minX()..maxX()).forEach { x ->
                    coords.add(Coordinate(x, y))
                }
            }
        }
        return coords.toList()
    }

    companion object {
        fun parse(s: String): Claim {
            val regex = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d)+$".toRegex()
            val matchEntire = regex.matchEntire(s)

            val (id, l, r, x, y) = matchEntire!!.groupValues
                .drop(1)
                .map { it.toInt() }

            return Claim(id, l, r, x, y)
        }
    }
}