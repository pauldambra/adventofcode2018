package com.dambra.adventofcode2018.day4

import java.time.LocalDateTime

interface GuardEvent {
    val eventDateTime: LocalDateTime
    val id: String
}

data class ShiftStarted(override val eventDateTime: LocalDateTime, override val id: String) :
    GuardEvent

data class GuardFellAsleep(override val eventDateTime: LocalDateTime, override val id: String) :
    GuardEvent

data class GuardWokeUp(override val eventDateTime: LocalDateTime, override val id: String) :
    GuardEvent