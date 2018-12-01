package com.dambra.adventofcode2018.day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FrequencyChanges {

    @Test
    fun `+1, -2, +3, +1 results in 3`() {
        val frequencyChanges = sequenceOf("+1", "+1", "+1")
        val result = frequencyChanges.resultingFrequency()
        assertThat(result).isEqualTo(3)
    }

    @Test
    fun`+1, +1, +1 results in  3`() {
        val frequencyChanges = sequenceOf("+1", "+1", "+1")
        val result = frequencyChanges.resultingFrequency()
        assertThat(result).isEqualTo(3)
    }

    @Test
    fun`+1, +1, -2 results in  0`() {
        val frequencyChanges = sequenceOf("+1", "+1", "-2")
        val result = frequencyChanges.resultingFrequency()
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun`-1, -2, -3 results in -6`() {
        val frequencyChanges = sequenceOf("-1", "-2", "-3")
        val result = frequencyChanges.resultingFrequency()
        assertThat(result).isEqualTo(-6)
    }

}

