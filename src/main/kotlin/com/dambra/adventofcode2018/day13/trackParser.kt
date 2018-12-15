package com.dambra.adventofcode2018.day13

fun parseTracks(track: String): Pair<MutableList<Cart>, MutableMap<Location, String>> {
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
    return Pair(cs, rs)
}