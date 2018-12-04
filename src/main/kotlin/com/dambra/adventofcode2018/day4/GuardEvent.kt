package com.dambra.adventofcode2018.day4

import java.time.LocalDateTime

interface GuardEvent {
    val dateParts: LocalDateTime
    val id: String
}

data class ShiftStarted(override val dateParts: LocalDateTime, override val id: String) :
    GuardEvent

data class GuardFellAsleep(override val dateParts: LocalDateTime, override val id: String) :
    GuardEvent

data class GuardWokeUp(override val dateParts: LocalDateTime, override val id: String) :
    GuardEvent