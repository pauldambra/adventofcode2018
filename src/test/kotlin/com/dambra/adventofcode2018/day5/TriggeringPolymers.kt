package com.dambra.adventofcode2018.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TriggeringPolymers {
    @Test
    fun `In "aA", a and A react, leaving nothing behind`() {
        val exampleInput = "aA"
        val result = exampleInput.triggerPolymer()
        assertThat(result).isEqualTo("")

    }
    @Test
    fun `In "abBA", bB destroys itself, leaving aA As above, this then destroys itself, leaving nothing`() {
        val exampleInput = "abBA"
        val result = exampleInput.triggerPolymer()
        assertThat(result).isEqualTo("")

    }
    @Test
    fun `In "abAB", no two adjacent units are of the same type, and so nothing happens`() {
        val exampleInput = "abAB"
        val result = exampleInput.triggerPolymer()
        assertThat(result).isEqualTo("abAB")

    }
    @Test
    fun `In "aabAAB", even though aa and AA are of the same type, their polarities match, and so nothing happens`() {
        val exampleInput = "aabAAB"
        val result = exampleInput.triggerPolymer()
        assertThat(result).isEqualTo("aabAAB")

    }
}

private fun String.triggerPolymer(): String {
    return this
}
