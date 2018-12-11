package com.dambra.adventofcode2018.day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SkyWriting {
    private val puzzleInput: List<String> = javaClass.getResource("/day10Part1Input.txt")
        .readText()
        .split("\n")

    private val exampleInput = listOf(
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
    fun `stars with large coordinates can be parsed from input`() {
        val input = "position=< 9123,  100345> velocity=< 0,  2>"
        val star = Star.parse(input)
        assertThat(star.position).isEqualTo(Position(9123,100345))
        assertThat(star.velocity).isEqualTo(Velocity(0,2))
    }

    @Test
    fun `single star can be drawn`() {
        val starMap = StarMap(listOf(
            "position=< 0,  0> velocity=< 0,  2>"
        ))

        assertThat(starMap.toString()).isEqualTo("""
#
""".trimIndent())
    }

    @Test
    fun `simple star map can be drawn`() {
        val starMap = StarMap(listOf(
            "position=<-1,  0> velocity=< 0,  2>",
            "position=< 1,  0> velocity=< 0,  2>"
        ))

        assertThat(starMap.toString()).isEqualTo("""
#.#
""".trimIndent())
    }

    @Test
    fun `the stars can be drawn at 0 seconds`() {

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

    @Test
    fun `the stars can be drawn at 1 seconds`() {

        val starMap = StarMap(exampleInput)
        starMap.move()

        assertThat(starMap.toString()).isEqualTo("""........#....#....
......#.....#.....
#.........#......#
..................
....#.............
..##.........#....
....#.#...........
...##.##..#.......
......#.#.........
......#...#.....#.
#...........#.....
..#.....#.#.......""".trimIndent())
    }

    @Test
    fun `the example sky writing can be guessed at 3 seconds`() {
        var starMap = StarMap(exampleInput)
        val minimumAreaAt = starMap.seekSkyWriting()
        assertThat(minimumAreaAt).isEqualTo(3)

        //reset it
        starMap = StarMap(exampleInput)
        starMap.move(minimumAreaAt)

        assertThat(starMap.toString()).isEqualTo("""
#...#..###
#...#...#.
#...#...#.
#####...#.
#...#...#.
#...#...#.
#...#...#.
#...#..###
        """.trimIndent())
    }

    @Test
    fun `the puzzle input sky writing can be searched for writing`() {
        var starMap = StarMap(puzzleInput)
        val minimumAreaAt = starMap.seekSkyWriting()
        assertThat(minimumAreaAt).isEqualTo(10831)

        //reset it
        starMap = StarMap(puzzleInput)
        starMap.move(minimumAreaAt)

        assertThat(starMap.toString()).isEqualTo("""
#####...#....#..#####......###...####...#.......#####...######
#....#..#....#..#....#......#...#....#..#.......#....#..#.....
#....#..#....#..#....#......#...#.......#.......#....#..#.....
#....#..#....#..#....#......#...#.......#.......#....#..#.....
#####...######..#####.......#...#.......#.......#####...#####.
#....#..#....#..#...........#...#..###..#.......#.......#.....
#....#..#....#..#...........#...#....#..#.......#.......#.....
#....#..#....#..#.......#...#...#....#..#.......#.......#.....
#....#..#....#..#.......#...#...#...##..#.......#.......#.....
#####...#....#..#........###.....###.#..######..#.......######
""".trimIndent())
    }
}

data class BoundingBox(val minX: Long, val maxX: Long, val minY: Long, val maxY: Long) {
    fun size() = maxX - minX + maxY - minY
}

class StarMap(startingPositions: List<String>) {
    private val stars: MutableList<Star> = mutableListOf()
    private var byY: Map<Long, Map<Long, List<Star>>>
    private var startingBounds: BoundingBox

    init {
        val parsed = startingPositions.map(Star.Companion::parse)
        stars.addAll(parsed)

        startingBounds = getBoundingBox(stars)

        byY = starsByCoordinate()
    }

    private fun starsByCoordinate(): Map<Long, Map<Long, List<Star>>> {
        return stars
            .groupBy { it.position.y }
            .map { it.key to it.value.groupBy { ys -> ys.position.x } }
            .toMap()
    }

    private fun getBoundingBox(stars: MutableList<Star>): BoundingBox {
        val minX = stars.minBy { it.position.x }!!.position.x
        val maxX = stars.maxBy { it.position.x }!!.position.x

        val minY = stars.minBy { it.position.y }!!.position.y
        val maxY = stars.maxBy { it.position.y }!!.position.y

        return BoundingBox(minX, maxX, minY, maxY)
    }

    fun move(seconds: Int = 1) {
        for (x in 1..seconds) {
            stars.forEach(Star::move)
        }
    }

    fun seekSkyWriting(): Int {

        var mapIsGrowing = false
        var lastBoundingBox = startingBounds

        var seconds = 0

        while (!mapIsGrowing) {
            stars.forEach(Star::move)
            seconds++
            val newBoundingBox = getBoundingBox(stars)
            if (newBoundingBox.size() > lastBoundingBox.size()) {
                mapIsGrowing = true
            } else {
                lastBoundingBox = newBoundingBox
            }
        }
        return seconds - 1
    }

    override fun toString(): String {

        byY = starsByCoordinate()

        val bounds = getBoundingBox(stars)

        return (bounds.minY..bounds.maxY).fold("") { o, y ->
            val hasAnyForThisRow = byY.containsKey(y)
            val row = (bounds.minX..bounds.maxX).fold("") { s, x ->
                if (hasAnyForThisRow && byY[y]!!.contains(x)) {
                    "$s#"
                } else {
                    "$s."
                }
            }
            "$o\n$row"
        }.trimIndent()
    }
}

data class Position(val x: Long, val y: Long)

data class Velocity(val x: Int, val y: Int)

data class Star(var position: Position, val velocity: Velocity) {
    fun move() {
        position = Position(position.x + velocity.x, position.y + velocity.y)
    }

    companion object {
        fun parse(s: String): Star {
            // e.g. from "position=< 9,  1> velocity=< 0,  2>"

            val parts = s
                .trimEnd('>')
                .split("> v")
                .map { it.split("=<") }
                .map { it.drop(1) }
                .map { it.map { s->s.split(",") } }
                .flatten()
                .map { it.map { s -> s.trim() } }

            val posX = parts[0][0].toLong()
            val posY = parts[0][1].toLong()

            val velX = parts[1][0].toInt()
            val velY = parts[1][1].toInt()

            return Star(Position(posX, posY), Velocity(velX, velY))
        }
    }
}