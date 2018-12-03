package com.dambra.adventofcode2018.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FindingOverlappingClaims {

    @Test
    fun `can be done from parsed claims`() {
        val claim = Claim.parse("#123 @ 3,2: 5x4")
        assertThat(claim).isEqualTo(Claim(123, 3, 2, 5, 4))
    }
}

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int) {
    companion object {
        fun parse(s: String): Claim {
            val regex = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d)+$".toRegex()
            val matchEntire = regex.matchEntire(s)

            val (id,l,r,x,y) = matchEntire!!.groupValues.drop(1).map { it.toInt() }

            return Claim(id, l, r, x, y)
        }
    }
}
