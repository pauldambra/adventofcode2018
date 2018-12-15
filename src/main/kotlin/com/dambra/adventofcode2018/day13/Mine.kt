package com.dambra.adventofcode2018.day13

class Mine(track: String) {
    var carts: List<Cart>
    val rails: Map<Location, String>

    init {
        val cs = mutableListOf<Cart>()
        val rs = mutableMapOf<Location, String>()

        track.split("\n").forEachIndexed { y, line ->
            line.split("").drop(1).dropLast(1).forEachIndexed { x, s ->
                val l = Location(x, y)
                if ("v^<>".contains(s)) {
                    cs.add(Cart(s, l))
                    rs[l] = when (s) {
                        "v" -> "|"
                        ">" -> "-"
                        "^" -> "|"
                        "<" -> "-"
                        else -> throw Exception("can't convert $s to a piece of railing")
                    }
                } else {
                    rs[l] = s
                }

            }
        }
        carts = cs.toList()
        rails = rs.toMap()
    }

    fun crashLocation(): Location {

        while (!hasCollided()) {
            carts.forEach { it.move(rails) }
        }

        return collision()
    }

    private fun hasCollided() = carts.groupingBy { it.location }.eachCount().any { it.value == 2 }
    private fun collision() = carts.groupingBy { it.location }.eachCount().filter { it.value == 2 }.entries.first().key
}