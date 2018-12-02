package com.dambra.adventofcode2018.day2

data class LetterFrequency(
    val exactlyTwoTimesLetter: List<String> = emptyList(),
    val exactlyThreeTimesLetter: List<String> = emptyList()
) {
    constructor(m: Map<Int, List<String>>) : this(
        m.getOrDefault(2, emptyList()),
        m.getOrDefault(3, emptyList())
    )
}

fun String.findLetterFrequency() = sequenceOf(this).findLetterFrequency().first()

fun Sequence<String>.findLetterFrequency() =
    this.map {
        println("processing $it")
        it.groupingBy { c -> c }.eachCount()
            .filter { x -> x.value == 2 || x.value == 3 }
            .entries
            .map { m -> m.value to m.key }
            .fold(mutableMapOf<Int, List<String>>()) { acc, p ->
                val current = acc.getOrDefault(p.first, emptyList())
                acc[p.first] = current + p.second.toString()
                acc
            }
            .toMap()
    }.map(::LetterFrequency)