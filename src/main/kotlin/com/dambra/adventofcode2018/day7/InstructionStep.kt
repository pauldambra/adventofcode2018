package com.dambra.adventofcode2018.day7

class InstructionStep(val step: String) {
    val previousSteps: MutableSet<InstructionStep> = mutableSetOf()
    val nextSteps: MutableSet<InstructionStep> = mutableSetOf()

    var completed: Boolean = false

    fun canBeCompleted(completedSteps: MutableSet<InstructionStep>) =
        previousSteps.isEmpty() || previousSteps.all { completedSteps.contains(it) }

    fun timeToComplete() = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(step) + 60 + 1

    override fun toString(): String {
        return "InstructionStep(" +
                "step='$step', " +
                "previousSteps=${previousSteps.map { it.step }}, " +
                "nextSteps=${nextSteps.map { it.step }}, " +
                "completed=$completed)"
    }
}

data class AssemblyReport(val stepsOrder: String, val secondsToComplete: Int)

fun assemble(startingSteps: List<InstructionStep>, workers: Int = 1): AssemblyReport {
    startingSteps.forEach { it.completed = true }

    var route = ""

    val nextOptions = mutableSetOf<InstructionStep>()
    val completedSteps = mutableSetOf<InstructionStep>()
    startingSteps.forEach { nextOptions += it }

    val seconds = 0

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

    return AssemblyReport(route, 0)
}

fun List<Pair<String, String>>.orderInstructions(): List<InstructionStep> {

    val steps = mutableMapOf<String, InstructionStep>()

    this.forEach {
        val current = steps.getOrPut(it.first) { InstructionStep(it.first) }
        val next = steps.getOrPut(it.second) { InstructionStep(it.second) }

        current.nextSteps.add(next)
        next.previousSteps.add(current)
    }

    //doesn't have to be a single starting instruction!
    return steps.values.filter { it.previousSteps.isEmpty() }

}

fun List<String>.parse(): List<Pair<String, String>> {

    val instructionPattern = """^Step (.).*step (.).*$""".toRegex()
    return this.filterNot { it.isEmpty() }
        .map { instructionPattern.matchEntire(it) }
        .map { it!!.groupValues[1] to it.groupValues[2] }
}