package com.dambra.adventofcode2018.day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SaferMineRemovesCollidedCarts {

    @Test
    fun `the safe mine ends with a single cart at 6,4`() {
        val mine = SafeMine(
            """
        />-<\
        |   |
        | /<+-\
        | | | v
        \>+</ |
          |   ^
          \<->/
        """.trimIndent()
        )

        val cart = mine.lastCartStanding()

        assertThat(cart.location).isEqualTo(Location(6,4))
    }

    @Test
    fun `find last cart standing in the puzzle input`() {
        val puzzleInput: String = javaClass.getResource("/day13Part1Input.txt").readText()
        val mine = SafeMine(puzzleInput)
        val lastCart = mine.lastCartStanding()
        assertThat(lastCart.location).isEqualTo(Location(88, 64))
    }
}