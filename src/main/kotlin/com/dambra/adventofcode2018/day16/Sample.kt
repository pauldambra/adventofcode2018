package com.dambra.adventofcode2018.day16

data class Sample(val registersBefore: List<Int>, val instruction: Instruction, val registersAfter: List<Int>) {
    companion object {
        fun parse(s: String): Sample {
            val lines = s.split("\n")

            val registers = parseRegisterLine(lines[0])
            val instruction = Instruction.from(lines[1].trim())
            val expectedResult = parseRegisterLine(lines[2])

            return Sample(registers, instruction, expectedResult)
        }

        private fun parseRegisterLine(s: String) = s
            .split("[")[1]
            .trimEnd(']')
            .split(",")
            .map { it.trim() }
            .map { it.toInt() }

    }
}