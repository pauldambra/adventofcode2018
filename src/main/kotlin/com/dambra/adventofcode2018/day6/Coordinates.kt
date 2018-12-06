package com.dambra.adventofcode2018.day6

import kotlin.math.abs

data class Coordinate(val id: Int, val x: Int, val y: Int) {
    fun manhattanDistanceTo(other: Coordinate): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun distancesTo(cs: List<Coordinate>) =
        cs.map { Pair(it, it.manhattanDistanceTo(this)) }
        .sortedBy { it.second }
}

fun List<String>.toCoordinates(): List<Coordinate> {
    return this
        .asSequence()
        .map { it.split(",") }
        .map { Pair(it[0].trim(), it[1].trim()) }
        .mapIndexed { index, ss -> Coordinate(index, ss.first.toInt(), ss.second.toInt()) }
        .toList()
}