package com.dambra.adventofcode2018.day5

fun String.triggerPolymer(): String {

    val removedIndices = mutableSetOf<Int>()

    var triggeredPolymerCollapse: Boolean
    do {
        triggeredPolymerCollapse = false

        for (i in 0 until this.length) {
            val left = this[i]
            if (removedIndices.contains(i)) {
                continue
            }

            val next = findNext(i, removedIndices)
            if (next > this.length - 1) {
                continue
            }
            val right = this[next]

            if (haveSameType(
                    left,
                    right
                ) && haveOppositePolarities(left, right)
            ) {
                removedIndices.add(i)
                removedIndices.add(next)
                triggeredPolymerCollapse = true
            }
        }
    } while (triggeredPolymerCollapse)

    return this
        .filterIndexed { i, _ -> !removedIndices.contains(i) }
}

fun String.improvePolymer(): String {
    var shortestImprovedPolymer = this
//    println("improving $this")
    "abcdefghijklmnopqrstuvwxyz".asSequence()
        .forEach {polymerType ->
            if (this.any { x -> x.equals(polymerType, ignoreCase = true) }) {
//                println("filtering $this for $polymerType")

                val removedIndices = mutableSetOf<Int>()

                var triggeredPolymerCollapse: Boolean
                do {
                    triggeredPolymerCollapse = false

                    for (i in 0 until this.length) {
                        val left = this[i]
                        if (removedIndices.contains(i)) {
                            continue
                        }

                        //filter polymer type
                        if (left.equals(polymerType, ignoreCase = true)) {
                            removedIndices.add(i)
                            triggeredPolymerCollapse = true
                            continue
                        }

                        val next = findNext(i, removedIndices)
                        if (next > this.length - 1) {
                            continue
                        }
                        val right = this[next]

//                        println("comparing $left and $right")

                        if (haveSameType(left, right) && haveOppositePolarities(left, right)) {
                            removedIndices.add(i)
                            removedIndices.add(next)
                            triggeredPolymerCollapse = true
                        }
                    }
                } while (triggeredPolymerCollapse)

                val s =  this.filterIndexed { i, _ -> !removedIndices.contains(i) }

//                println("$s has length ${s.length}, current best is $shortestImprovedPolymer with ${shortestImprovedPolymer.length}")
                if (s.length < shortestImprovedPolymer.length) {
                    shortestImprovedPolymer = s
                }
            }
        }

    return shortestImprovedPolymer
}

private fun findNext(i: Int, removedIndices: MutableSet<Int>): Int {
    var next = i + 1
    while (removedIndices.contains(next)) {
        next += 1
    }
    return next
}

private fun haveSameType(left: Char, right: Char) = left.equals(right, ignoreCase = true)

private fun haveOppositePolarities(left: Char, right: Char) =
    (left.isUpperCase() && right.isLowerCase() || left.isLowerCase() && right.isUpperCase())