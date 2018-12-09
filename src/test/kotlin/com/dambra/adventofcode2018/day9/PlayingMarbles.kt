package com.dambra.adventofcode2018.day9

import com.dambra.adventofcode2018.repeat
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
}

class Circle {
    var currentMarble: Marble
        private set

    init {
        currentMarble = Marble(0)
        currentMarble.setClockwise(currentMarble)
        currentMarble.setCounterClockwise(currentMarble)
    }

    fun place(marble: Marble) {
        currentMarble = currentMarble.clockwiseMarble
        val before = currentMarble
        val after = currentMarble.clockwiseMarble

        before.setClockwise(marble)
        marble.setCounterClockwise(before)

        marble.setClockwise(after)
        after.setCounterClockwise(marble)

        currentMarble = marble
    }

    fun removeScoringMarble(): Marble {
        var m = currentMarble
        for (it in 1..7) {
            m = m.counterClockwiseMarble
        }

        val before = m.counterClockwiseMarble
        val after = m.clockwiseMarble

        after.setCounterClockwise(before)
        before.setClockwise(after)
        currentMarble = after

        return m
    }
}

class Marble(val marbleNumber: Int) {
    lateinit var counterClockwiseMarble: Marble
        private set

    lateinit var clockwiseMarble: Marble
        private set

    fun setCounterClockwise(m: Marble) {
        this.counterClockwiseMarble = m
    }

    fun setClockwise(m: Marble) {
        this.clockwiseMarble = m
    }

    override fun toString(): String {
        return "Marble(${counterClockwiseMarble?.marbleNumber}<- $marbleNumber -> ${clockwiseMarble?.marbleNumber})"
    }


}

class Game(private val gameDescription: String) {
    private val playerScores: MutableMap<Int, Int> = mutableMapOf()

    fun highScore(): Int {
        val parts = gameDescription.split(" ")
        val numberOfPlayers = parts.first().toInt()
        val lastMarble = parts[parts.size - 2].toInt()

        val playerTurns = (0 until numberOfPlayers).asSequence().repeat()

        val marbles = (1..lastMarble).asSequence()

        val eachTurn = playerTurns.zip(marbles)

        val circle = Circle()

        eachTurn.forEach {
            if (it.second % 23 == 0) {
                playerScores.putIfAbsent(it.first, 0)
                val score = playerScores[it.first]!!

                val m: Marble = circle.removeScoringMarble()

                playerScores[it.first] = score + it.second + m.marbleNumber
            } else {
                circle.place(Marble(it.second))
            }
        }
        println(playerScores)
        return playerScores.values.sortedByDescending { it }.first()
    }


}
