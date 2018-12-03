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
    fun `can de done for the example inputs`() {
        val claims = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        ).map { Claim.parse(it) }

        val overlaps = Fabric().findOverlaps(claims)

        assertThat(overlaps).containsAll(
            listOf(
                Coordinate(3, 3),
                Coordinate(4, 3),
                Coordinate(3, 4),
                Coordinate(4, 4)
            )
        )

        assertThat(Fabric().findOverlapArea(claims)).isEqualTo(4)
    }

    @Test
    fun `can be done for the puzzle input`() {
        val claims = javaClass.getResource("/day3Part1Input.txt")
            .readText()
            .split("\n")
            .map { Claim.parse(it) }

        val overlapArea = Fabric().findOverlapArea(claims)

        println("found overlap area = $overlapArea")

        assertThat(overlapArea).isGreaterThan(17318)
    }
}
