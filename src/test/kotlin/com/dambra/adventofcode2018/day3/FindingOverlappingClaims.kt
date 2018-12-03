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


        val findOverlapArea = Fabric(
            listOf(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"
            )
        ).findOverlapArea()

        assertThat(findOverlapArea).isEqualTo(4)
    }

    @Test
    fun `can find claims with no overlaps`() {

        val intactClaims = Fabric(
            listOf(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"
            )
        ).findIntactClaims()

        assertThat(intactClaims).containsExactly(3)
    }

    @Test
    fun `can be done for the puzzle input`() {
        val claims = javaClass.getResource("/day3Part1Input.txt")
            .readText()
            .split("\n")

        val overlapArea = Fabric(claims).findOverlapArea()

        println("found overlap area = $overlapArea")

        assertThat(overlapArea).isGreaterThan(17318)
    }

    @Test
    fun `can find claims with no overlaps in the puzzle input`() {
        val claims = javaClass.getResource("/day3Part1Input.txt")
            .readText()
            .split("\n")

        val intactClaims = Fabric(claims).findIntactClaims()

        assertThat(intactClaims).containsExactlyInAnyOrder(1097)
    }
}
