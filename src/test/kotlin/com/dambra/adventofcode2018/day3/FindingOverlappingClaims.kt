package com.dambra.adventofcode2018.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FindingOverlappingClaims {

    @Test
    fun `can be done from parsed claims`() {
        val claim = Claim.parse("#123 @ 3,2: 5x4")
        assertThat(claim).isEqualTo(Claim(123, 3, 2, 5, 4))
    }

    @Test
    fun `can de done for the example imputs`() {
        val claims = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        ).map { Claim.parse(it) }

        val overlaps = Fabric().findOverlaps(claims)

        assertThat(overlaps).containsExactly(Overlap(3, 3, 2, 2))
    }
}

class Fabric {
    fun findOverlaps(cs: List<Claim>): List<Overlap> {

        cs.forEach {left ->
            cs.forEach {
                right->
                if (left != right) { // don't compare with self
                    left.overlapWith(right)
                }
            }
        }

        return emptyList()
    }
}

data class Overlap(val left: Int, val top: Int, val width: Int, val height: Int)

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int) {
    fun overlapWith(other: Claim) {
        //this x runs from left to left + (width - 1)
        //the other runs from other.left to other.left + (width - 1)
        // so given left = 2 and width = 3 we have 2,3,4
        // and other.left = left = 4 and width = 1 gives 4
        // overlap is 4 -> left 3 width 1
        val xs = xs()
        val otherXs = other.xs()
        val xOverlap = xs.intersect(otherXs)

        val ys = ys()
        val otherYs = other.ys()
        val yOverlap = ys.intersect(otherYs)

        println(this)
        println(other)
        println("x: $xOverlap")
        println("y: $yOverlap")
    }

    private fun xs() = left..(left + (width - 1))
    private fun ys() = top..(top + (height - 1))

    companion object {
        fun parse(s: String): Claim {
            val regex = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d)+$".toRegex()
            val matchEntire = regex.matchEntire(s)

            val (id, l, r, x, y) = matchEntire!!.groupValues
                .drop(1)
                .map { it.toInt() }

            return Claim(id, l, r, x, y)
        }
    }
}
