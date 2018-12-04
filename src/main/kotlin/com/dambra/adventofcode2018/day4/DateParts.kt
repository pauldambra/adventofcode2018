package com.dambra.adventofcode2018.day4

import java.time.LocalDateTime

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

    fun asLocalDateTime() =
        LocalDateTime.of(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )!!
}