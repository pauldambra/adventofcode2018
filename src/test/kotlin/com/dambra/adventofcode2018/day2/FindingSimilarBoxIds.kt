package com.dambra.adventofcode2018.day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FindingSimilarBoxIds {

    @Test
    fun `is possible in the example input`() {
        val boxIds = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
        )

        val similarIds = boxIds.similarIds()

        assertThat(similarIds.first()).isEqualTo("fgij")
    }

    @Test
    fun `is possible with the puzzle input`() {
        val boxIds = javaClass.getResource("/day2Part1Input.txt")
            .readText()
            .split("\n")

        val similarIds = boxIds.similarIds()

        assertThat(similarIds).isEqualTo(listOf("icxjvbrobtunlelzpdmfkahgs"))
    }
}

