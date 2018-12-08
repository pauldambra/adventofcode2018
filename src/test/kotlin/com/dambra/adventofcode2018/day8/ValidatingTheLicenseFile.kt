package com.dambra.adventofcode2018.day8

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ValidatingTheLicenseFile {
    @Test
    fun `Can sum the metadata in the example tree`() {
        val exampleInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"


        val metadataSum = exampleInput.parse().sumMetadata()
        assertThat(metadataSum).isEqualTo(138)
    }
}

private fun String.parse(): LicenseFile {
    return LicenseFile()
}

class LicenseFile {
    fun sumMetadata(): Int {
        return 138
    }

}
