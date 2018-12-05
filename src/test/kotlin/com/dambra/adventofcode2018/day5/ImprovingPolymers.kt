package com.dambra.adventofcode2018.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ImprovingPolymers {
    private val puzzleInput: String = javaClass.getResource("/day5Part1Input.txt").readText()

    @Test
    fun `Improving polymer, leaving dbCBcD behind`() {
        val exampleInput = "dabAcCaCBAcCcaDA"
        val result = exampleInput.improvePolymer()
        assertThat(result).isEqualTo("daDA")
    }

    @Test
    fun `with the puzzle input`() {
        val result = this.puzzleInput.improvePolymer()
        assertThat(result.length).isEqualTo(5524)
    }
}
