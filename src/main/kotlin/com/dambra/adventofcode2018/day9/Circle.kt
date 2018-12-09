package com.dambra.adventofcode2018.day9

class Circle {
    var currentMarble: Marble
        private set

    init {
        currentMarble = Marble(0)
        currentMarble.clockwiseMarble = currentMarble
        currentMarble.counterClockwiseMarble = currentMarble
    }

    fun place(marble: Marble) {
        currentMarble = currentMarble.clockwiseMarble
        val before = currentMarble
        val after = currentMarble.clockwiseMarble

        before.clockwiseMarble = marble
        marble.counterClockwiseMarble = before

        marble.clockwiseMarble = after
        after.counterClockwiseMarble = marble

        currentMarble = marble
    }

    fun removeScoringMarble(): Marble {
        val m = rotateSevenCounterClockwise()

        val before = m.counterClockwiseMarble
        val after = m.clockwiseMarble

        after.counterClockwiseMarble = before
        before.clockwiseMarble = after

        currentMarble = after

        return m
    }

    private fun rotateSevenCounterClockwise(): Marble {
        var m = currentMarble
        for (it in 1..7) {
            m = m.counterClockwiseMarble
        }
        return m
    }
}