package com.dambra.adventofcode2018.day13

class Mine(track: String) {
    var carts: List<Cart>
    val rails: Map<Location, String>

    init {
        val (cs, rs) = parseTracks(track)
        carts = cs.toList()
        rails = rs.toMap()
    }

    fun crashLocation(): Location {

        while (!hasCollided()) {
            carts.forEach { it.move(rails) }
        }

        return collision()
    }

    private fun hasCollided() = carts
        .groupingBy { it.location }
        .eachCount()
        .any { it.value == 2 }

    private fun collision() = carts
        .groupingBy { it.location }
        .eachCount()
        .filter { it.value == 2 }
        .entries
        .first()
        .key
}