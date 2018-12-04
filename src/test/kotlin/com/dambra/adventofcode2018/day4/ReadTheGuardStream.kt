package com.dambra.adventofcode2018.day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ReadTheGuardStream {

    private val puzzleInput = javaClass.getResource("/day4Part1Input.txt")
        .readText()
        .split("\n")

    private val exampleInput = listOf(
        "[1518-11-01 00:00] Guard #10 begins shift",
        "[1518-11-01 00:05] falls asleep",
        "[1518-11-01 00:25] wakes up",
        "[1518-11-01 00:30] falls asleep",
        "[1518-11-01 00:55] wakes up",
        "[1518-11-01 23:58] Guard #99 begins shift",
        "[1518-11-02 00:40] falls asleep",
        "[1518-11-02 00:50] wakes up",
        "[1518-11-03 00:05] Guard #10 begins shift",
        "[1518-11-03 00:24] falls asleep",
        "[1518-11-03 00:29] wakes up",
        "[1518-11-04 00:02] Guard #99 begins shift",
        "[1518-11-04 00:36] falls asleep",
        "[1518-11-04 00:46] wakes up",
        "[1518-11-05 00:03] Guard #99 begins shift",
        "[1518-11-05 00:45] falls asleep",
        "[1518-11-05 00:55] wakes up"
    )

    @Test
    fun `with the example events`() {

        val guardEvents = GuardPattern.parse(exampleInput)

        val actual = GuardPost(guardEvents).bestGuardStrategyOne

        assertThat(actual).isEqualTo(240)
    }

    @Test
    fun `with the example events for strategy 2`() {

        val guardEvents = GuardPattern.parse(exampleInput)

        val actual = GuardPost(guardEvents).bestGuardStrategyTwo

        assertThat(actual).isEqualTo(4455)
    }

    @Test
    fun `with the puzzle input for part one`() {
        val guardEvents = GuardPattern.parse(puzzleInput)

        val mostAsleepGuardStrategyOne = GuardPost(guardEvents).bestGuardStrategyOne

        assertThat(mostAsleepGuardStrategyOne).isEqualTo(14346)
    }

    @Test
    fun `with the puzzle input for part two`() {
        val guardEvents = GuardPattern.parse(puzzleInput)

        val guardWithMostFrequentMinuteAsleep = GuardPost(guardEvents).bestGuardStrategyTwo

        assertThat(guardWithMostFrequentMinuteAsleep).isEqualTo(5705)
    }
}

