package com.dambra.adventofcode2018.day19

import com.dambra.adventofcode2018.day16.ElfsemblyInstruction
import com.dambra.adventofcode2018.day16.Instruction.Companion.from
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.dambra.adventofcode2018.day16.Instruction
import com.dambra.adventofcode2018.day16.Operation
import com.dambra.adventofcode2018.day16.OperationMatcher

internal class ElfsemblyJumps {
    private val puzzleInput = """
#ip 5
addi 5 16 5
seti 1 1 2
seti 1 8 1
mulr 2 1 3
eqrr 3 4 3
addr 3 5 5
addi 5 1 5
addr 2 0 0
addi 1 1 1
gtrr 1 4 3
addr 5 3 5
seti 2 6 5
addi 2 1 2
gtrr 2 4 3
addr 3 5 5
seti 1 2 5
mulr 5 5 5
addi 4 2 4
mulr 4 4 4
mulr 5 4 4
muli 4 11 4
addi 3 2 3
mulr 3 5 3
addi 3 13 3
addr 4 3 4
addr 5 0 5
seti 0 8 5
setr 5 5 3
mulr 3 5 3
addr 5 3 3
mulr 5 3 3
muli 3 14 3
mulr 3 5 3
addr 4 3 4
seti 0 9 0
seti 0 9 5
    """.trimIndent()

    private val exampleInput = """
#ip 0
seti 5 0 1
seti 6 0 2
addi 0 1 0
addr 1 2 3
setr 1 0 0
seti 8 0 4
seti 9 0 5
    """.trimIndent()

    @Test
    fun `example input halts with 6 in register 0`() {
        val jp = JumpingProgram(exampleInput)
        val finalRegisters = jp.untilHalts()
        assertThat(finalRegisters[0]).isEqualTo(6)
    }

    @Test
    fun `puzzle input halts with something in register 0`() {
        val jp = JumpingProgram(puzzleInput)
        val finalRegisters = jp.untilHalts()
        assertThat(finalRegisters[0]).isEqualTo(6)
    }
}

data class DecodedInstruction(
    val operation: Operation,
    override val inputOne: Int,
    override val inputTwo: Int,
    override val output: Int
) :
    ElfsemblyInstruction {


    companion object {
        fun from(s: String): DecodedInstruction {
            val parts = s.split(" ")
            val opcode = parts[0]
            val operation = OperationMatcher.operations[opcode]!!

            val (b, c, d) = parts.drop(1).map { it.toInt() }

            return DecodedInstruction(operation, b, c, d)
        }
    }
}

class JumpingProgram(private val program: String) {
    fun untilHalts(): List<Int> {
        var registers = mutableListOf(0, 0, 0, 0, 0, 0)

        val lines = program.split("\n")
        val instructionPointerIndex = lines.first().split(" ").last().trim().toInt()
        val instructions = lines.drop(1).map(DecodedInstruction.Companion::from)

        var instructionPointer: Int = registers[instructionPointerIndex]
        while (instructionPointer < instructions.size) {
            registers[instructionPointerIndex] = instructionPointer

            val next = instructions[instructionPointer]
            registers = next.operation.applyTo(next, registers).toMutableList()

            instructionPointer = registers[instructionPointerIndex]
            instructionPointer += 1
        }

        return registers
    }

}
