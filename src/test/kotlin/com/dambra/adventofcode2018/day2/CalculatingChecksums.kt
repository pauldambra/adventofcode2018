package com.dambra.adventofcode2018.day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CalculatingChecksums {

    @Test
    fun `from the example input the checksum is 12`() {
        val boxChecksum = listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
        )
            .map { it.findLetterFrequency() }
            .boxChecksum()

        assertThat(boxChecksum).isEqualTo(12)
    }

    @Test
    fun `from the part 1 puzzle input`() {

        val boxChecksum = javaClass.getResource("/day2Part1Input.txt")
            .readText()
            .split("\n")
            .map(String::findLetterFrequency)
            .toList()
            .boxChecksum()

        assertThat(boxChecksum).isEqualTo(6888)
    }
}
