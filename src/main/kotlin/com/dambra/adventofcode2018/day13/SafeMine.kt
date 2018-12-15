package com.dambra.adventofcode2018.day13

class SafeMine(track: String) {
    var carts: List<Cart>

    private val rails: Map<Location, String>

    init {
        val (cs, rs) = parseTracks(track)
        carts = cs.toList()
        rails = rs.toMap()
    }

    fun lastCartStanding(): Cart {
        do {
            activeCarts().forEach {
                it.move(rails)
                removeCollisions()
            }
        } while (activeCarts().count() > 1)

        return activeCarts().first()
    }

    private fun removeCollisions() =
        activeCarts()
            .groupBy { it.location }
            .filter { it.value.size > 1 }
            .flatMap { it.value }
            .forEach { it.crashed = true }

    private fun activeCarts() = carts.filterNot { it.crashed }

}