package com.dambra.adventofcode2018.day1

fun Sequence<kotlin.String>.resultingFrequency(): Int {
    return this.map(String::toInt).sum()
}