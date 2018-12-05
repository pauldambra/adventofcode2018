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

    @Test
    fun `with the puzzle input`() {
        val puzzleInput = javaClass.getResource("/day5Part1Input.txt").readText()
        val result = puzzleInput.triggerPolymer()
        assertThat(result.length).isEqualTo(9686)
    }
}

private fun String.triggerPolymer(): String {

    val removedIndices = mutableSetOf<Int>()

    var triggeredPolymerCollapse: Boolean
    do {
        triggeredPolymerCollapse = false

        for (i in 0 until this.length) {
            val left = this[i]
            if (removedIndices.contains(i)) {
                continue
            }

            val next = findNext(i, removedIndices)
            if (next > this.length - 1) {
                continue
            }
            val right = this[next]

            if (haveSameType(left, right) && haveOppositePolarities(left, right)) {
                removedIndices.add(i)
                removedIndices.add(next)
                triggeredPolymerCollapse = true
            }
        }
    } while (triggeredPolymerCollapse)

    return this
        .filterIndexed { i, _ -> !removedIndices.contains(i) }
}

private fun findNext(i: Int, removedIndices: MutableSet<Int>): Int {
    var next = i + 1
    while (removedIndices.contains(next)) {
        next += 1
    }
    return next
}

private fun haveSameType(left: Char, right: Char) = left.equals(right, ignoreCase = true)

private fun haveOppositePolarities(left: Char, right: Char) =
    (left.isUpperCase() && right.isLowerCase() || left.isLowerCase() && right.isUpperCase())
