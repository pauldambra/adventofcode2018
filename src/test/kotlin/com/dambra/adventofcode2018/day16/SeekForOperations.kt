package com.dambra.adventofcode2018.day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SeekForOperations {
    @Test
    fun `can seek for possible operations`() {
        val results = OperationMatcher().matchSample(
            """
Before: [3, 2, 1, 1]
9 2 1 2
After:  [3, 2, 2, 1]
        """.trimIndent()
        )

        assertThat(results).hasSize(3) // this sample could match 3 opcodes
    }

    @Test
    fun `can seek for operations in puzzle input`() {
        val results = puzzleInputToOperationCounts()

        val samplesThatMatchAtLeastThreeOperations = results.filter { it.size >= 3 }

        assertThat(samplesThatMatchAtLeastThreeOperations.size).isEqualTo(521)
    }

    private fun puzzleInputToOperationCounts(): List<List<Match>> {
        val puzzleInput: List<String> = javaClass.getResource("/day16Part1Input.txt").readText()
            .split("\n")
            .filterNot { it.isEmpty() }

        val samples = puzzleInput.fold(mutableListOf<MutableList<String>>()) { acc, s ->
            if (acc.isEmpty() || acc.last().size == 3) {
                acc.add(mutableListOf())
            }
            if (s.isNotEmpty()) {
                acc.last().add(s)
            }

            acc
        }
            .map { it.joinToString("\n") }
            .map(Sample.Companion::parse)

        val opcodes = samples.map { it.instruction.opcode }

        println("opcodes run from ${opcodes.min()} to ${opcodes.max()}")

        val operationMatcher = OperationMatcher()

        return samples.map(operationMatcher::matchSample)
    }

    @Test
    fun `can try and figure out which operation each code is`() {
        val opCodes = getOpCodes()
        println(opCodes)

        val sampleProgram: List<String> = javaClass.getResource("/day16Part2Input.txt")
            .readText()
            .split("\n")

        var registers = listOf(0,0,0,0)
        val operationMatcher = OperationMatcher()
        sampleProgram
            .map(Instruction.Companion::from)
            .forEach { instr ->
                val op = operationMatcher.operations[opCodes[instr.opcode]]!!
                registers = op.applyTo(instr, registers)
        }

        assertThat(registers).isEqualTo(listOf(594,3,4,594))

    }

    private fun getOpCodes(): Map<Int, String> {
        val results = puzzleInputToOperationCounts().flatten()

        val byInstruction = results
            .groupBy { it.instruction }

        val matchedOpcodes = byInstruction.map {
            it.key.opcode to it.value.map { x -> x.operation }.distinct().toMutableList()
        }

        val opCodes = mutableMapOf<Int, String>()
        //opcodes run from 0 to 15


        while (opCodes.size < 16) {

            val distinctOpCodeMatch = matchedOpcodes
                .map { it.first to it.second.filterNot(opCodes.values::contains) }
                .first { it.second.size == 1 }

            opCodes[distinctOpCodeMatch.first] = distinctOpCodeMatch.second.first()

        }
        return opCodes.toMap()
    }
}