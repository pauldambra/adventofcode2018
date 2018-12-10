package com.dambra.adventofcode2018.day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SkyWriting {
    @Test
    fun `example motion can be described`() {
        //At `0 seconds`, each point has the position given.
        //Each second, each point's velocity is added to its position.
        //So, a point with velocity `<1, -2>` is moving to the right, but is moving upward twice as quickly.
        // If this point's initial position were `<3, 9>`, after 3 seconds,
        // its position would become `<6, 3>`.

        val position = Position(3, 9)
        val velocity = Velocity(1, -2)

        val particle = Star(position, velocity)

        particle.move()
        particle.move()
        particle.move()

        assertThat(particle.position).isEqualTo(Position(6, 3))
    }

    @Test
    fun `stars can be parsed from input`() {
        val input = "position=< 9,  1> velocity=< 0,  2>"
        val star = Star.parse(input)
        assertThat(star.position).isEqualTo(Position(9,1))
        assertThat(star.velocity).isEqualTo(Velocity(0,2))
    }

    @Test
    fun `the stars can be drawn`() {
        val exampleInput = listOf(
            "position=< 9,  1> velocity=< 0,  2>",
            "position=< 7,  0> velocity=<-1,  0>",
            "position=< 3, -2> velocity=<-1,  1>",
            "position=< 6, 10> velocity=<-2, -1>",
            "position=< 2, -4> velocity=< 2,  2>",
            "position=<-6, 10> velocity=< 2, -2>",
            "position=< 1,  8> velocity=< 1, -1>",
            "position=< 1,  7> velocity=< 1,  0>",
            "position=<-3, 11> velocity=< 1, -2>",
            "position=< 7,  6> velocity=<-1, -1>",
            "position=<-2,  3> velocity=< 1,  0>",
            "position=<-4,  3> velocity=< 2,  0>",
            "position=<10, -3> velocity=<-1,  1>",
            "position=< 5, 11> velocity=< 1, -2>",
            "position=< 4,  7> velocity=< 0, -1>",
            "position=< 8, -2> velocity=< 0,  1>",
            "position=<15,  0> velocity=<-2,  0>",
            "position=< 1,  6> velocity=< 1,  0>",
            "position=< 8,  9> velocity=< 0, -1>",
            "position=< 3,  3> velocity=<-1,  1>",
            "position=< 0,  5> velocity=< 0, -1>",
            "position=<-2,  2> velocity=< 2,  0>",
            "position=< 5, -2> velocity=< 1,  2>",
            "position=< 1,  4> velocity=< 2,  1>",
            "position=<-2,  7> velocity=< 2, -2>",
            "position=< 3,  6> velocity=<-1, -1>",
            "position=< 5,  0> velocity=< 1,  0>",
            "position=<-6,  0> velocity=< 2,  0>",
            "position=< 5,  9> velocity=< 1, -2>",
            "position=<14,  7> velocity=<-2,  0>",
            "position=<-3,  6> velocity=< 2, -1>"
        )

        val starMap = StarMap(exampleInput)

        assertThat(starMap.toString()).isEqualTo("""........#.............
................#.....
.........#.#..#.......
......................
#..........#.#.......#
...............#......
....#.................
..#.#....#............
.......#..............
......#...............
...#...#.#...#........
....#..#..#.........#.
.......#..............
...........#..#.......
#...........#.........
...#.......#..........""".trimIndent())
    }


}

class StarMap(startingPositions: List<String>) {
    private val stars: MutableList<Star> = mutableListOf()
    init {
        stars.addAll(startingPositions.map(Star.Companion::parse))
    }

    override fun toString(): String {
        return stars.map { it.position }.joinToString("'")
    }
}

data class Position(val x: Int, val y: Int) {
    fun moveBy(velocity: Velocity) = Position(x + velocity.x, y + velocity.y)
}

data class Velocity(val x: Int, val y: Int)

data class Star(var position: Position, val velocity: Velocity) {
    fun move() {
        position = position.moveBy(velocity)
    }

    companion object {
        fun parse(s: String): Star {
            // e.g. from "position=< 9,  1> velocity=< 0,  2>"

            val posX = s.substring(10 until 12).trim().toInt()
            val posY = s.substring(14 until 16).trim().toInt()

            val velX = s.substring(28 until 30).trim().toInt()
            val velY = s.substring(32 until 34).trim().toInt()

            return Star(Position(posX, posY), Velocity(velX, velY))
        }
    }
}