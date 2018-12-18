package com.dambra.adventofcode2018.day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ReadingTheInstructions {
    private val puzzleInput: List<String> = javaClass.getResource("/day7Part1Input.txt")
        .readText()
        .split("\n")

    @Test
    fun `can parse the instructions`() {
        val instructions = listOf(
            "Step C must be finished registersBefore step A can begin.",
            "Step C must be finished registersBefore step F can begin.",
            "Step A must be finished registersBefore step B can begin.",
            "Step A must be finished registersBefore step D can begin.",
            "Step B must be finished registersBefore step E can begin.",
            "Step D must be finished registersBefore step E can begin.",
            "Step F must be finished registersBefore step E can begin.",
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

    @Test
    fun `can assemble the graph of instructions`() {
        val instructions = listOf(
            "Step C must be finished registersBefore step A can begin.",
            "Step C must be finished registersBefore step F can begin.",
            "Step A must be finished registersBefore step B can begin.",
            "Step A must be finished registersBefore step D can begin.",
            "Step B must be finished registersBefore step E can begin.",
            "Step D must be finished registersBefore step E can begin.",
            "Step F must be finished registersBefore step E can begin.",
            ""
        )
        val assemblyReport = assemble(instructions.parse().orderInstructions())

        assertThat(assemblyReport.stepsOrder).isEqualTo("CABDFE")
    }

    @Test
    fun `can assemble the graph of instructions from the puzzle input`() {
        val assemblyReport = assemble(puzzleInput.parse().orderInstructions())

        assertThat(assemblyReport.stepsOrder).isEqualTo("BITRAQVSGUWKXYHMZPOCDLJNFE")
    }
}
