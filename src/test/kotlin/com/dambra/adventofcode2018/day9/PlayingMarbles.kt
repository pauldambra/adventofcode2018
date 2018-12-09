package com.dambra.adventofcode2018.day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PlayingMarbles {
    val puzzleInput = "459 players; last marble is worth 72103 points"

    @Test
    fun `when 9 players end at 25 points`() {
        val exampleInput = "9 players; last marble is worth 25 points"

        assertThat(Game(exampleInput).highScore()).isEqualTo(32)
    }

    @Test
    fun `10 players last marble is worth 1618 points high score is 8317`() {
        val exampleInput = "10 players; last marble is worth 1618 points"

        assertThat(Game(exampleInput).highScore()).isEqualTo(8317)
    }

    @Test
    fun `13 players last marble is worth 7999 points high score is 146373`() {
        val exampleInput = "13 players; last marble is worth 7999 points"

        assertThat(Game(exampleInput).highScore()).isEqualTo(146373)
    }

    @Test
    fun `17 players last marble is worth 1104 points high score is 2764`() {
        val exampleInput = "17 players; last marble is worth 1104 points"

        assertThat(Game(exampleInput).highScore()).isEqualTo(2764)
    }

    @Test
    fun `21 players last marble is worth 6111 points high score is 54718`() {
        val exampleInput = "21 players; last marble is worth 6111 points"

        assertThat(Game(exampleInput).highScore()).isEqualTo(54718)
    }

    @Test
    fun `30 players last marble is worth 5807 points high score is 37305`() {
        val exampleInput = "30 players; last marble is worth 5807 points"

        assertThat(Game(exampleInput).highScore()).isEqualTo(37305)
    }

    @Test
    fun `can play with the puzzle input`() {
        val highScore = Game(puzzleInput).highScore()
        assertThat(highScore).isEqualTo(388131)
    }

    @Test
    fun `can play with the 100x larger puzzle input`() {
        val highScore = Game(puzzleInput, 100).highScore()
        assertThat(highScore).isEqualTo(3239376988L)
    }
}

