package com.dambra.adventofcode2018.day20

import com.dambra.adventofcode2018.day18.Coord
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class DiscoveringPaths {
    @Test
    fun `for simple path`() {
        /*
        ^WNE$ creates this facility map
            #####
            #.|.#
            #-###
            #.|X#
            #####
         */
        val path = "^WNE$"
        val map = Facility(path)
        val result = map.longestShortestPath()
        assertThat(result).isEqualTo(3)
    }

//    @Test
//    fun `for a path with a branch`() {
//        /*
//        generates
//        #########
//        #.|.|.|.#
//        #-#######
//        #.|.|.|.#
//        #-#####-#
//        #.#.#X|.#
//        #-#-#####
//        #.|.|.|.#
//        #########
//         */
//        val path = "^ENWWW(NEEE|SSE(EE|N))$"
//        val map = Facility(path)
//        val result = map.longestShortestPath()
//        assertThat(result).isEqualTo(10)
//    }
}


class Facility(private val path: String) {

    fun longestShortestPath(): Int {
        require(path.startsWith("^"))
        require(path.endsWith("$"))

        val rooms = mutableSetOf<Coord>()

        var x = 0
        var y = 0
        path.drop(1).dropLast(1)
            .asSequence()
            .map(Char::toString)
            .forEach {
                println("it $it")
                when (it) {
                    "W" -> {
                        rooms.add(Coord(--x, y))
                    }
                    "N" -> {
                        rooms.add(Coord(x, --y))
                    }
                    "E" -> {
                        rooms.add(Coord(++x, y))
                    }
                    "S" -> {
                        rooms.add(Coord(x, ++y))
                    }
                    else -> throw Exception("unknown instruction: $it")
                }
            }

        val route: MutableList<MutableList<Coord>>
        val roomsToWalk = ArrayDeque<Coord>()
        roomsToWalk.push(Coord(0, 0))

//        while (roomsToWalk.isNotEmpty()) {
//
//        }

        return 3
    }
}
