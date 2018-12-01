package com.dambra.adventofcode2018.day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PuzzleInputs {

    private val puzzleInput = PuzzleInputs::class.java.getResource("/day1Part1Input.txt")

    @Test
    fun `part one`() {
        val result = puzzleInput
            .readText()
            .split("\n")
            .resultingFrequency()

        assertThat(result).isEqualTo(533)
    }

    @Test
    fun `part two`() {
        val result = puzzleInput
            .readText()
            .split("\n")
            .firstRepeatedFrequency()

        assertThat(result).isEqualTo(73272)
    }
}