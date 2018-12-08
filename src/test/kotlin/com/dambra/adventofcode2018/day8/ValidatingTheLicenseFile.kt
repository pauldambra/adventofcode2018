package com.dambra.adventofcode2018.day8

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class ValidatingTheLicenseFile {
    private val puzzleInput: String = javaClass.getResource("/day8Part1Input.txt")
        .readText()

    @Test
    fun `can sum the metadata in a simplified one node tree`() {
        val exampleInput = "0 3 1 1 2"

        val licenseFile = LicenseFile.parse(exampleInput)
        val metadataSum = licenseFile.sumMetadata()
        assertThat(metadataSum).isEqualTo(4)
    }

    @Test
    fun `can sum metadata in a tree with one empty child node`() {
        val exampleInput = "1 3 0 0 1 1 2"

        val metadataSum = LicenseFile.parse(exampleInput).sumMetadata()
        assertThat(metadataSum).isEqualTo(4)
    }

    @Test
    fun `can sum metadata in a tree with one simple child node`() {
        val exampleInput = "1 3 0 1 1 1 1 2"

        val metadataSum = LicenseFile.parse(exampleInput).sumMetadata()
        assertThat(metadataSum).isEqualTo(5)
    }

    @Test
    fun `Can sum the metadata in the example tree`() {
        val exampleInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"

        val metadataSum = LicenseFile.parse(exampleInput).sumMetadata()
        assertThat(metadataSum).isEqualTo(138)
    }

    @Test
    fun `Can find the root node value in the example tree`() {
        val exampleInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"

        val rootNodeValue = LicenseFile.parse(exampleInput).rootNodeValue()
        assertThat(rootNodeValue).isEqualTo(66)
    }

    @Test
    fun `Can sum the metadata in the puzzle input tree`() {
        val metadataSum = LicenseFile.parse(puzzleInput).sumMetadata()
        assertThat(metadataSum).isEqualTo(36627)
    }

    @Test
    fun `Can find the root node value in the puzzle input tree`() {
        val rootNodeValue = LicenseFile.parse(puzzleInput).rootNodeValue()
        assertThat(rootNodeValue).isEqualTo(16695)
    }
}

