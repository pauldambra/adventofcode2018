package com.dambra.adventofcode2018.day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class WithMultipleWorkers {
    private val puzzleInput: List<String> = javaClass.getResource("/day7Part1Input.txt")
        .readText()
        .split("\n")

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
        val assemblyReport = assemble(instructions.parse().orderInstructions(), 2)

        assertThat(assemblyReport.stepsOrder).isEqualTo("CABFDE")
        assertThat(assemblyReport.secondsToComplete).isEqualTo(15)
    }
}

