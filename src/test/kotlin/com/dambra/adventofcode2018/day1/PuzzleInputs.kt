package com.dambra.adventofcode2018.day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PuzzleInputs {
    @Test
    fun `part one`() {
        val result = this.javaClass.getResource("/day1Part1Input.txt")
            .readText()
            .split("\n")
            .resultingFrequency()

        assertThat(result).isEqualTo(533)
    }

    @Test
    fun `part two`() {
        val result = this.javaClass.getResource("/day1Part1Input.txt")
            .readText()
            .split("\n")
            .firstRepeatedFrequency()

        assertThat(result).isEqualTo(73272)
    }
}