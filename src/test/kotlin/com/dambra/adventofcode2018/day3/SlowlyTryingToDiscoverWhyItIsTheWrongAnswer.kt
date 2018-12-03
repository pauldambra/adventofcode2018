package com.dambra.adventofcode2018.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SlowlyTryingToDiscoverWhyItIsTheWrongAnswer {
    @Test
    fun `coordinates are in as expected`() {
        val c = Claim(id=2, left=3, top=1, width=4, height=4)
        val contains = c.contains(Coordinate(4, 5))
        assertThat(contains).isFalse()
    }

    @Test
    fun `the first two`() {
        val claims = listOf(
            "#1 @ 151,671: 11x15",
            "#2 @ 887,913: 28x22"
        ).map { Claim.parse(it) }

        val overlaps = Fabric().findOverlaps(claims)

        assertThat(overlaps).isEmpty()

    }
}