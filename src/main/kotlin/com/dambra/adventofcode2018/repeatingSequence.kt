package com.dambra.adventofcode2018

fun <T> List<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }