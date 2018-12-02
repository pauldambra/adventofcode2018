package com.dambra.adventofcode2018.day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PuzzleInputs {

    @Test
    fun `part one`() {
        val result = javaClass.getResourceAsStream("/day1Part1Input.txt")
            .bufferedReader()
            .useLines(Sequence<String>::resultingFrequency)

        assertThat(result).isEqualTo(533)
    }

    @Test
    fun `part two`() {
        val result = javaClass.getResourceAsStream("/day1Part1Input.txt")
            .bufferedReader()
            .useLines(Sequence<String>::firstRepeatedFrequency)

        assertThat(result).isEqualTo(73272)
    }
}