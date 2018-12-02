package com.dambra.adventofcode2018.day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

data class BoxIdExamples(val id: String, val letterFrequency: LetterFrequency)

internal class CountingLetters {
    @Test
    fun `in strings works as expected`() {
        listOf(
            BoxIdExamples("abcdef", LetterFrequency()),
            BoxIdExamples("bababc", LetterFrequency(listOf("a"), listOf("b"))),
            BoxIdExamples("abbcde", LetterFrequency(listOf("b"))),
            BoxIdExamples("abcccd", LetterFrequency(exactlyThreeTimesLetter = listOf("c"))),
            BoxIdExamples("aabcdd", LetterFrequency(listOf("a", "d"))),
            BoxIdExamples("abcdee", LetterFrequency(listOf("e"))),
            BoxIdExamples("ababab", LetterFrequency(exactlyThreeTimesLetter = listOf("a", "b")))
        ).forEach {
            val found = it.id.findLetterFrequency()

            assertThat(found.exactlyTwoTimesLetter).isEqualTo(it.letterFrequency.exactlyTwoTimesLetter)
            assertThat(found.exactlyThreeTimesLetter).isEqualTo(it.letterFrequency.exactlyThreeTimesLetter)
        }
    }
}


