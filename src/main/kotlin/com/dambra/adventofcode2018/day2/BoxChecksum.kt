package com.dambra.adventofcode2018.day2

fun List<LetterFrequency>.boxChecksum(): Int {

    var twos = 0
    var threes = 0

    this.forEach {
        if (it.exactlyTwoTimesLetter.isNotEmpty()) twos++
        if (it.exactlyThreeTimesLetter.isNotEmpty()) threes++
    }

    return twos * threes
}