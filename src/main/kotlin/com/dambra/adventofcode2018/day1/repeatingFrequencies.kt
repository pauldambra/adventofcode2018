package com.dambra.adventofcode2018.day1

import java.lang.Exception

private fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

fun List<String>.firstRepeatedFrequency(): Int {

    var frequency = 0
    val seen = mutableSetOf(0)

    this.asSequence().repeat()
        .map { it: String -> it.toInt() }
        .forEach {
            val next = frequency + it
            if (seen.contains(next)) {
                return next
            } else {
                frequency = next
                seen.add(frequency)
            }
        }

    throw Exception("we both know that this is such an ugly way to do control flow")
}