package com.dambra.adventofcode2018.day1

import java.lang.Exception

fun List<kotlin.String>.resultingFrequency(): Int {
    return this.map(String::toInt).sum()
}

private fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

fun List<String>.firstRepeatedFrequency(): Int {

    var frequency = 0
    var seen = mutableSetOf(0)

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

    throw Exception("this is such an ugly way to do control flow")
}