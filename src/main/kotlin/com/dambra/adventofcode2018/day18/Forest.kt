package com.dambra.adventofcode2018.day18

class Forest(map: String) {
    private var acres: MutableMap<Coord, Acre>
    private val maxX: Int
    private val maxY: Int

    fun resourceValue() : Int {
        val trees = acres
            .values
            .count { it.type == Acre.trees }
        val lumberyard = acres
            .values
            .count { it.type == Acre.lumberyard }
        return trees * lumberyard

    }

    init {
        val xs = mutableMapOf<Coord, Acre>()
        map.split("\n").forEachIndexed { y, row ->
            row.split("").drop(1).dropLast(1).forEachIndexed { x, s ->
                val coord = Coord(x, y)
                xs[coord] = Acre(s)
            }
        }
        acres = xs
        maxX = acres.keys.maxBy { it.x }!!.x
        maxY = acres.keys.maxBy { it.y }!!.y
    }

    private val seenStates = mutableSetOf(acres)
    private var firstRepeatingState: MutableMap<Coord, Acre>? = null
    private var firstRepeatAt = 0

    fun after(minutes: Int): String {

        var minute = 0
        while (minute < minutes) {

            acres = acres.map { x: Map.Entry<Coord, Acre> ->

                val surroundingTypes = x.key
                    .surroundingCoords()
                    .map { it -> acres.getOrElse(it) { null } }
                    .filterNot { it == null }
                    .map { it!!.type }
                    .groupingBy { it }
                    .eachCount()
                x.key to x.value.tick(surroundingTypes)
            }
                .toMap()
                .toMutableMap()

            if (firstRepeatingState != null) {
                if (firstRepeatingState == acres) {
                    // first repeat is seen at minite `firstRepeatAt`
                    // we know the forest is in a loop of cycle length minute - firstRepeatAt
                    // we want to jump forwards by to the highest value
                    // that is a multiple of `cycle length` after minute and lower than max minutes
                    val cycleLength = minute - firstRepeatAt
                    val tillEnd = 1000000000 - minute
                    val endOfCycleClosestToMax = tillEnd - (tillEnd % cycleLength)
                    minute += endOfCycleClosestToMax
                }

            } else if (!seenStates.add(acres)) {
                firstRepeatingState = acres
                firstRepeatAt = minute
            }

            minute++
        }

        return print(acres)
    }

    private fun print(forest: Map<Coord, Acre>): String {
        var s = ""
        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                s += forest[Coord(x, y)]!!.type
            }
            s += "\n"
        }
        return s.trimIndent()
    }

}