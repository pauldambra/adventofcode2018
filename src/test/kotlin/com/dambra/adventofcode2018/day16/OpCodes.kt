package com.dambra.adventofcode2018.day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class OpCodes {
    @Test
    fun `can make instruction`() {
        val instruction = Instruction.from("9 2 1 2")
        assertThat(instruction).isEqualTo(Instruction(9, 2, 1, 2))
    }

    /**
     * opcode addi adds register A and value B,
     * storing the result in register C,
     * and the instruction addi 0 7 3 is encountered,
     * it would add 7 to the value contained by register 0 and store the sum in register 3,
     * never modifying registers 0, 1, or 2 in the process.
     */
    @Test
    fun addi() {
        val registers = listOf(0, 0, 0, 0)
        val instruction = Instruction.from("9 0 7 3")
        val after: List<Int> = Addi().applyTo(instruction, registers)
        assertThat(after).isEqualTo(listOf(0, 0, 0, 7))
    }

    @Test
    fun addr() {
        val registers = listOf(0, 1, 4, 0)
        val instruction = Instruction.from("9 1 2 3")
        val after: List<Int> = Addr().applyTo(instruction, registers)
        assertThat(after).isEqualTo(listOf(0, 1, 4, 5))
    }

    @Test
    fun muli() {
        val registers = listOf(0, 2, 4, 0)
        val instruction = Instruction.from("9 1 3 3")
        val after: List<Int> = Muli().applyTo(instruction, registers)
        assertThat(after).isEqualTo(listOf(0, 2, 4, 6))
    }

    @Test
    fun mulr() {
        val registers = listOf(0, 2, 4, 0)
        val instruction = Instruction.from("9 1 2 3")
        val after: List<Int> = Mulr().applyTo(instruction, registers)
        assertThat(after).isEqualTo(listOf(0, 2, 4, 8))
    }
}