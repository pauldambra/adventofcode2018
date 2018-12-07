package com.dambra.adventofcode2018.day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ReadingTheInstructions {
    @Test
    fun `can parse the instructions`() {
        val instructions = listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin.",
            ""
        )
        val parsedInstructions = listOf(
            Pair("C", "A"),
            Pair("C", "F"),
            Pair("A", "B"),
            Pair("A", "D"),
            Pair("B", "E"),
            Pair("D", "E"),
            Pair("F", "E")
        )
        assertThat(instructions.parse()).isEqualTo(parsedInstructions)
    }
}

private fun List<String>.parse(): List<Pair<String, String>> {

    val instructionPattern = """^Step (.).*step (.).*$""".toRegex()
    return this.filterNot { it.isEmpty() }
        .map { instructionPattern.matchEntire(it) }
        .map { it!!.groupValues[1] to it.groupValues[2] }
}
