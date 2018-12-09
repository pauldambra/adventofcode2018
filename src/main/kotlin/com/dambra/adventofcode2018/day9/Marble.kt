package com.dambra.adventofcode2018.day9

class Marble(val marbleNumber: Long) {
    lateinit var counterClockwiseMarble: Marble
    lateinit var clockwiseMarble: Marble
}