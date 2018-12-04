package com.dambra.adventofcode2018.day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.time.LocalDateTime

internal class ReadTheGuardStream {

    @Test
    fun `with the example events`() {
        val events = listOf(
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
        ).sorted()

        println("sorted guard events")
        println(events)

        val guardEvents = GuardPattern.parse(events)



        val guardPost = GuardPost(guardEvents)
        val actual = guardPost.bestGuardStrategyOne()

        assertThat(actual).isEqualTo(240)
    }

    @Test
    fun `with the puzzle input`() {
        val events = javaClass.getResource("/day4Part1Input.txt")
            .readText()
            .split("\n")
            .sorted()

        println("sorted guard events")
        println(events)

        val guardEvents = GuardPattern.parse(events)
        val guardPost = GuardPost(guardEvents)
        val actual = guardPost.bestGuardStrategyOne()

        // most asleep was 797
        assertThat(actual).isEqualTo(14346)
    }
}

class GuardPost(guardEvents: List<GuardEvent>) {
    fun bestGuardStrategyOne(): Int {
        return guardBestMinute.first.toInt() * guardBestMinute.second
    }

    private var guardBestMinute: Pair<String, Int>

    init {
        val guards = mutableMapOf<String, MutableList<Int>>()

        var fellAsleep: LocalDateTime? = null

        for (ge in guardEvents) {
            when (ge) {
                is ShiftStarted -> guards.putIfAbsent(ge.id, mutableListOf())
                is GuardFellAsleep -> fellAsleep = ge.dateParts
                is GuardWokeUp -> {
                    val wokeUp = ge.dateParts
                    var current = fellAsleep!!
                    while (!current.isEqual(wokeUp)) {
                        guards[ge.id]!!.add(current.minute)
                        current = current.plusMinutes(1)
                    }
                }
                else -> throw Exception("impossible event")
            }
        }

        val guardMostAsleep = guards.maxBy { it.value.size }!!
        println("guard most asleep was ${guardMostAsleep.key}")

        val minuteAsleepFreq = guards[guardMostAsleep.key]!!
            .groupingBy { it }.eachCount()
            .toList()
            .sortedByDescending { (_,v) -> v }
            .first()
            .first

        this.guardBestMinute = Pair(guardMostAsleep.key, minuteAsleepFreq)

    }
}

data class DateParts(
    val year: String,
    val month: String,
    val day: String,
    val hour: String,
    val minute: String
) {

    companion object {
        fun parse(s: String): LocalDateTime {
            val parts = s.split(" ")
            val date = parts[0].split("-")
            val time = parts[1].split(":")
            return DateParts(date[0], date[1], date[2], time[0], time[1]).asLocalDateTime()
        }
    }

    fun asLocalDateTime()  =
        LocalDateTime.of(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )!!
}

interface GuardEvent {
    val dateParts: LocalDateTime
    val id: String
}
data class ShiftStarted(override val dateParts: LocalDateTime, override val id: String) : GuardEvent
data class GuardFellAsleep(override val dateParts: LocalDateTime, override val id: String) : GuardEvent
data class GuardWokeUp(override val dateParts: LocalDateTime, override val id: String) : GuardEvent

class GuardPattern {

    companion object {
        private val inputPattern = """^\[(.*)\] (.*)$""".toRegex()
        private lateinit var activeGuard: String

        fun parse(events: List<String>): List<GuardEvent> {
            return events
                .map {
                    val result = inputPattern.matchEntire(it)!!
                    Pair(result.groupValues[1], result.groupValues[2])
                }
                .map {
                    val dateParts = DateParts.parse(it.first)

                    when {
                        it.second.contains("begins shift") -> {
                            val guardId = it.second.split(" ")[1].trim('#')
                            activeGuard = guardId

                            ShiftStarted(
                                dateParts,
                                activeGuard
                            )
                        }
                        it.second.contains("falls asleep") ->
                            GuardFellAsleep(
                                dateParts,
                                activeGuard
                            )
                        it.second.contains("wakes up") ->
                            GuardWokeUp(
                                dateParts,
                                activeGuard
                            )
                        else -> {
                            throw Exception("unknown event source: $it")
                        }
                    }
                }
        }

    }
}

