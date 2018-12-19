package com.dambra.adventofcode2018.day19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ElfsemblyJumps {
    val puzzleInput = """
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

    val exampleInput = """
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
}

class JumpingProgram(program: String) {
    fun untilHalts(): List<Int> {
        return emptyList()
    }

}
