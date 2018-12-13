package com.dambra.adventofcode2018.day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GrowingPlants {
    private val exampleInput = """initial state: #..#.#..##......###...###

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #""".trimIndent()

    @Test
    fun `generation zero has the initial state`() {
        val genZero = Plants(exampleInput).generation(0)
        assertThat(genZero).isEqualTo("#..#.#..##......###...###")
    }

    @Test
    fun `generation zero can read initial state from other input too`() {
        val genZero = Plants(
            """initial state: ####.#..##......###...###

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #""".trimIndent()
        ).generation(0)

        assertThat(genZero).isEqualTo("####.#..##......###...###")
    }

    @Test
    fun `can apply rules to generate generation 1`() {
        val genOne = Plants(exampleInput).generation(1)

        assertThat(genOne).isNotEqualTo("#..#.#..##......###...###")
        assertThat(genOne).isEqualTo("#...#....#.....#..#..#..#")
    }

    @Test
    fun `can map a very simple generation`() {
        val genOne = Plants("""
            initial state: #..

            ..#.. => .
        """.trimIndent()).generation(1)

        assertThat(genOne).isEqualTo("...")
    }

    @Test
    fun `can map another very simple generation`() {
        val genOne = Plants("""
            initial state: #..###.

            ..#.. => #
            .#.#. => #
        """.trimIndent()).generation(1)

        assertThat(genOne).isEqualTo("#....#.")
    }


}

class Plants(instructions: String) {
    private val initialState: String
    private val rules: Map<String, String>

    init {
        val lines = instructions.split("\n")
        initialState = lines.first().split(": ").last()

        rules = lines.drop(1)
            .filterNot { it.isEmpty() }
            .map { it.split(" => ") }
            .map { it.first() to it.last() }
            .toMap()

        println(rules)
    }

    fun generation(i: Int): String {
        if (i == 0) return initialState

        val letters = toLetters(initialState).toMutableList()
        println("letters: $letters")

        letters.forEachIndexed { pot, letter ->
            val a = letterAt(letters, pot - 2)
            val b = letterAt(letters, pot - 1)
            val c = letterAt(letters, pot + 1)
            val d = letterAt(letters, pot + 2)

            val candidate = "$a$b$letter$c$d"
            if (pot == 4) {
                println(candidate)
            }
            if (rules.containsKey(candidate)) {
                println("matched rule $candidate at pot $pot which says change to ${rules[candidate]}")
                letters[pot] = rules[candidate]!!
            } else {
                letters[pot] = "."
            }

            println("index $pot changed from $letter to ${letters[pot]}")
        }

        println("finished as $letters")

        return letters.joinToString("")
    }

    private fun letterAt(letters: List<String>, index: Int): String {
        return when {
            index < 0 -> return "."
            index > letters.size - 1 -> return "."
            else -> letters[index]
        }
    }

    //this split makes empty head and tail
    private fun toLetters(s: String)
            = s.split("").drop(1).dropLast(1)

}
