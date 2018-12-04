package com.dambra.adventofcode2018.day4

import java.time.LocalDateTime

class GuardPost(guardEvents: List<GuardEvent>) {

    var bestGuardStrategyOne: Int = 0
        private set

    var bestGuardStrategyTwo: Int = 0
        private set

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

        guardThatSpentLongestAsleep(guards)
        guardThatSleptMostOnAnyParticularMinute(guards)

    }

    private fun guardThatSleptMostOnAnyParticularMinute(guards: MutableMap<String, MutableList<Int>>) {

        val guardMinuteFrequency = guards
            .filter { it.value.isNotEmpty() }
            .map {
                val minuteFrequencyForGuard = it.value
                    .groupingBy { m -> m }
                    .eachCount()
                    .toList()

                it.key to minuteFrequencyForGuard.maxBy { x -> x.second }!!
            }
            .maxBy { it.second.second }!!

        println("guard that is most frequently asleep on a given minute is ${guardMinuteFrequency.first}")
        println("that minute is ${guardMinuteFrequency.second}")

        val mostFrequentAsleepMinute = Pair(guardMinuteFrequency.first, guardMinuteFrequency.second.first)
        bestGuardStrategyTwo = mostFrequentAsleepMinute.first.toInt() * mostFrequentAsleepMinute.second
    }

    private fun guardThatSpentLongestAsleep(guards: MutableMap<String, MutableList<Int>>) {
        val guardMostAsleep = guards.maxBy { it.value.size }!!

        println("guard most asleep was ${guardMostAsleep.key}")

        val minuteAsleepFreq = guards[guardMostAsleep.key]!!
            .groupingBy { it }.eachCount()
            .toList()
            .sortedByDescending { (_, v) -> v }
            .first()
            .first

        println("the minute they slept most was ${minuteAsleepFreq}")

        val longestSleepingGuardBestMinute = Pair(guardMostAsleep.key, minuteAsleepFreq)
        bestGuardStrategyOne = longestSleepingGuardBestMinute.first.toInt() * longestSleepingGuardBestMinute.second
    }
}