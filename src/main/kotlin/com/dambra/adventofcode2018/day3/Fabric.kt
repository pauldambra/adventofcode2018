package com.dambra.adventofcode2018.day3

class Fabric {
    fun findOverlaps(cs: List<Claim>): List<Coordinate> {

        val ugh = HashMap<Coordinate, MutableList<Claim>>()

        val minX = cs.minBy { it.left }!!.minX()
        val maxX = cs.maxBy { it.left + it.width }!!.maxX()

        val minY = cs.minBy { it.top }!!.minY()
        val maxY = cs.maxBy { it.top + it.height }!!.maxY()

        println("minx: $minX and maxX: $maxX")
        println("minY: $minY and maxY: $maxY")

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                val c = Coordinate(x, y)
                ugh[c] = mutableListOf()
            }
        }

        cs.forEach { claim ->
            claim.coordinates().forEach {
                ugh[it]!!.add(claim)
            }
        }

        val final = ugh
            .filter { it.value.size > 1 }
        println(final)
        return final.map { it.key }
    }

    fun findOverlapArea(claims: List<Claim>): Int {
        return findOverlaps(claims).size
    }
}



