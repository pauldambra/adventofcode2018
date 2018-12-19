package com.dambra.adventofcode2018.day18

class Forest(map: String) {
    private var acres: MutableMap<ForestCoord, Acre>
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
        val xs = mutableMapOf<ForestCoord, Acre>()
        map.split("\n").forEachIndexed { y, row ->
            row.split("").drop(1).dropLast(1).forEachIndexed { x, s ->
                val coord = ForestCoord(x, y)
                xs[coord] = Acre(s)
            }
        }
        acres = xs
        maxX = acres.keys.maxBy { it.x }!!.x
        maxY = acres.keys.maxBy { it.y }!!.y
    }

    val seenStates = mutableSetOf(acres)
    var firstRepeatingState: MutableMap<ForestCoord, Acre>? = null
    var firstRepeatAt = 0

    fun after(minutes: Int): String {

        val start = System.currentTimeMillis()
        val times = mutableListOf<Long>()

        var minute = 0
        while (minute < minutes) {


            if (minute % 1000 == 0) {
                times.add(System.currentTimeMillis() - start)
                val average = times.average()
                println("average time for 1000 = $average milliseconds")
                println("anticipate 1,000,000,000 is ${(average/1000/60/60/24)*1000*1000} days")
            }

            acres = acres.map { x: Map.Entry<ForestCoord, Acre> ->

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
                    val cycleLength = minute - firstRepeatAt
                    minute += ((1000000000 - minute) / cycleLength) * cycleLength
                }

            } else if (!seenStates.add(acres)) {
                firstRepeatingState = acres
                firstRepeatAt = minute
            }

            minute++
        }

        return print(acres)
    }

    private fun print(forest: Map<ForestCoord, Acre>): String {
        var s = ""
        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                s += forest[ForestCoord(x, y)]!!.type
            }
            s += "\n"
        }
        return s.trimIndent()
    }

}