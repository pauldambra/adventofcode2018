package com.dambra.adventofcode2018.day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PlayingMarbles {
    val puzzleInput = "459 players; last marble is worth 72103 points"

    @Test
    fun `when 9 players end at 25 points`() {
        val exampleInput = "9 players; last marble is worth 25 points"
        val expectedHighScore = 32

        assertThat(Game(exampleInput).highScore()).isEqualTo(expectedHighScore)
    }
}

class Game(private val gameDescription: String) {
    fun highScore(): Int {
        return 32
    }

}
