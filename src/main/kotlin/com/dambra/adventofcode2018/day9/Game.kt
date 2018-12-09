package com.dambra.adventofcode2018.day9

import com.dambra.adventofcode2018.repeat

class Game(private val gameDescription: String, private val marbleMultiplier: Int = 1) {
    private val playerScores: MutableMap<Int, Long> = mutableMapOf()

    fun highScore(): Long {

        val circle = Circle()

        parsePlayerTurnsFromGameDescription()
            .forEach {
                if (it.second % 23 == 0L) {
                    score(it, circle)
                } else {
                    circle.place(Marble(it.second))
                }
            }

        return playerScores.values
            .sortedByDescending { it }
            .first()
    }

    private fun score(it: Pair<Int, Long>, circle: Circle) {
        val score = playerScores.getOrDefault(it.first, 0)

        val m = circle.removeScoringMarble()

        playerScores[it.first] = score + it.second + m.marbleNumber
    }

    private fun parsePlayerTurnsFromGameDescription(): Sequence<Pair<Int, Long>> {
        val parts = gameDescription.split(" ")
        val numberOfPlayers = parts.first().toInt()
        val lastMarble = parts[parts.size - 2].toLong() * marbleMultiplier

        val playerTurns = (0 until numberOfPlayers).asSequence().repeat()

        val marbles = (1..lastMarble).asSequence()

        return playerTurns.zip(marbles)
    }


}