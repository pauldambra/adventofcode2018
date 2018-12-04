package com.dambra.adventofcode2018.day4

import java.time.LocalDateTime

class GuardPattern {

    companion object {
        private val inputPattern = """^\[(.*)\] (.*)$""".toRegex()
        private lateinit var activeGuard: String

        fun parse(events: List<String>) = events
            .sorted()
            .map(Companion::guardStringToPair)
            .map(Companion::toGuardEvent)

        private fun toGuardEvent(it: Pair<String, String>): GuardEvent {
            val dateParts = DateParts.parse(it.first)

            return when {
                it.second.contains("begins shift") -> asShiftStarted(
                    it,
                    dateParts
                )
                it.second.contains("falls asleep") -> asGuardFellAsleep(
                    dateParts
                )
                it.second.contains("wakes up") -> asGuardWokeUp(
                    dateParts
                )
                else -> throw Exception("unknown event source: $it")
            }
        }

        private fun asGuardWokeUp(dateParts: LocalDateTime) = GuardWokeUp(
            dateParts,
            activeGuard
        )

        private fun asGuardFellAsleep(dateParts: LocalDateTime) = GuardFellAsleep(
            dateParts,
            activeGuard
        )

        private fun asShiftStarted(it: Pair<String, String>, dateParts: LocalDateTime): ShiftStarted {
            activeGuard = it.second.split(" ")[1].trim('#')
            return ShiftStarted(
                dateParts,
                activeGuard
            )
        }

        private fun guardStringToPair(it: String): Pair<String, String> {
            val result = inputPattern.matchEntire(it)!!
            return Pair(result.groupValues[1], result.groupValues[2])
        }

    }
}