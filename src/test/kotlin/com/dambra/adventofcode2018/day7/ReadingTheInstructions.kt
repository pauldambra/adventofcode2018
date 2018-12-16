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
        val order: String = instructions.parse().assemble()

//        assertThat(order).isEqualTo("CABDFE")
    }

    @Test
    fun `can assemble the graph of instructions from the puzzle input`() {
//        val order: String = puzzleInput.parse().assemble()

//        assertThat(order).isEqualTo("CABDFE")
    }
}

class InstructionStep(val step: String) {
    val previousSteps: MutableSet<InstructionStep> = mutableSetOf()
    val nextSteps: MutableSet<InstructionStep> = mutableSetOf()

    var completed: Boolean = false

    fun canBeCompleted(completedSteps: MutableSet<InstructionStep>) =
        previousSteps.isEmpty() || previousSteps.all { completedSteps.contains(it) }

    override fun toString(): String {
        return "InstructionStep(" +
                "step='$step', " +
                "previousSteps=${previousSteps.map { it.step }}, " +
                "nextSteps=${nextSteps.map { it.step }}, " +
                "completed=$completed)"
    }


}

private fun List<Pair<String, String>>.assemble(): String {

    val steps = mutableMapOf<String, InstructionStep>()

    this.forEach {
        val current = steps.getOrPut(it.first) { InstructionStep(it.first) }
        val next = steps.getOrPut(it.second) { InstructionStep(it.second) }

        current.nextSteps.add(next)
        next.previousSteps.add(current)
    }

    val startsWith = steps.values.first { it.previousSteps.isEmpty() }

    startsWith.completed = true

    var route = ""

    val nextOptions = mutableSetOf<InstructionStep>()
    val completedSteps = mutableSetOf<InstructionStep>()
    nextOptions.add(startsWith)

    while (nextOptions.any()) {
        println("route is $route")
        println("next options are: $nextOptions")

        val sortedOptions = nextOptions.sortedBy { it.step }
        println("sorted options $sortedOptions")
        val possibleOptions = sortedOptions
            .filter { it.canBeCompleted(completedSteps) }

        val current = possibleOptions.first()


        route += current.step
        completedSteps.add(current)
        nextOptions.remove(current)

        if (current.nextSteps.any()) {
            nextOptions.addAll(current.nextSteps)
        }
    }

    return route
}

private fun List<String>.parse(): List<Pair<String, String>> {

    val instructionPattern = """^Step (.).*step (.).*$""".toRegex()
    return this.filterNot { it.isEmpty() }
        .map { instructionPattern.matchEntire(it) }
        .map { it!!.groupValues[1] to it.groupValues[2] }
}
