package com.dambra.adventofcode2018.day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class InstructionSteps {
    @Test
    fun `time taken for C is 63`() {
        val x = InstructionStep("C").timeToComplete()
        assertThat(x).isEqualTo(63)
    }

    @Test
    fun `time taken for Z is 86`() {
        val x = InstructionStep("Z").timeToComplete()
        assertThat(x).isEqualTo(86)
    }
}