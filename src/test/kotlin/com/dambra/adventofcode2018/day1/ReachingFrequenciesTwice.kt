package com.dambra.adventofcode2018.day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class ReachingFrequenciesTwice {
    @Test
    fun `+1, -2, +3, +1 reaches 2 twice`() {
        val result = listOf("+1", "-2", "+3", "+1").firstRepeatedFrequency()
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `+1, -1 reaches 0 twice`() {
        val result = listOf("+1", "-1").firstRepeatedFrequency()
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `+3, +3, +4, -2, -4 reaches 10 twice`() {
        val result = listOf("+3", "+3", "+4", "-2", "-4").firstRepeatedFrequency()
        assertThat(result).isEqualTo(10)
    }

    @Test
    fun `-6, +3, +8, +5, -6 reaches 5 twice`() {
        val result = listOf("-6", "+3", "+8", "+5", "-6").firstRepeatedFrequency()
        assertThat(result).isEqualTo(5)
    }

    @Test
    fun `+7, +7, -2, -7, -4 reaches 14 twice`() {
        val result = listOf("+7", "+7", "-2", "-7", "-4").firstRepeatedFrequency()
        assertThat(result).isEqualTo(14)
    }
}
