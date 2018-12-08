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

        val licenseFile = exampleInput.parse()
        val metadataSum = licenseFile.sumMetadata()
        assertThat(metadataSum).isEqualTo(4)
    }

    @Test
    fun `can sum metadata in a tree with one empty child node`() {
        val exampleInput = "1 3 0 0 1 1 2"

        val metadataSum = exampleInput.parse().sumMetadata()
        assertThat(metadataSum).isEqualTo(4)
    }

    @Test
    fun `can sum metadata in a tree with one simple child node`() {
        val exampleInput = "1 3 0 1 1 1 1 2"

        val metadataSum = exampleInput.parse().sumMetadata()
        assertThat(metadataSum).isEqualTo(5)
    }

    @Test
    fun `Can sum the metadata in the example tree`() {
        val exampleInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"

        val metadataSum = exampleInput.parse().sumMetadata()
        assertThat(metadataSum).isEqualTo(138)
    }

    @Test
    fun `Can sum the metadata in the puzzle input tree`() {
        val metadataSum = puzzleInput.parse().sumMetadata()
        assertThat(metadataSum).isEqualTo(138)
    }
}


data class LicenseNode(
    val childrenCount: Int,
    val metadataCount: Int,
    val metadata: List<Int>
) {
    fun metadataSum() = metadata.sum()
}

class LicenseNodeBuilder {
    private var processedChildren: Int = 0
    private var metadata: MutableList<Int> = mutableListOf()

    fun isComplete() = !requiresChildrenCount()
            && !requiresMetadataCount()
            && !requiresChildrenProcessing()
            && hasMetadata()

    fun requiresChildrenProcessing(): Boolean {
        return childrenCount!! > processedChildren
    }

    fun addChildProcessing() {
        processedChildren++
    }

    fun addMetadata(metadata: Int) {
        this.metadata.add(metadata)
    }

    private var metadataCount: Int? = null
    private fun hasMetadata() = metadata.size == metadataCount
    fun requiresMetadataCount() = metadataCount == null

    fun addMetadataCount(metadataCount: Int) {
        this.metadataCount = metadataCount
    }

    private var childrenCount: Int? = null
    fun requiresChildrenCount() = childrenCount == null

    fun addChildrenCount(childrenCount: Int) {
        this.childrenCount = childrenCount
    }

    fun build(): LicenseNode {
        println("children count: $childrenCount")
        println("metadata count: $metadataCount")
        println("meta data: $metadata")
        return LicenseNode(childrenCount!!, metadataCount!!, metadata)
    }

}

private fun String.parse(): LicenseFile {
    // first char is children count
    // next character is metadata count
    // remaining chars are either the children or the metadata

    val completed = mutableListOf<LicenseNode>()

    val builderStack = ArrayDeque<LicenseNodeBuilder>()
    builderStack.push(LicenseNodeBuilder())

    val i = this.split(" ")
        .asSequence()
        .map { it.toInt() }
        .iterator()

    var currentBuilder: LicenseNodeBuilder? = LicenseNodeBuilder()

    while (i.hasNext()) {

        currentBuilder = if (currentBuilder!!.isComplete()) {
            println("completing node")
            completeNode(currentBuilder, completed)
            builderStack.pop()
        } else {
            step(currentBuilder, builderStack, i)
        }
    }
    completeNode(currentBuilder!!, completed) //final node should be ready now
    return LicenseFile(completed)
}

private fun completeNode(
    currentBuilder: LicenseNodeBuilder,
    completed: MutableList<LicenseNode>
) {
    println("found complete builder $currentBuilder")
    val licenseNode = currentBuilder.build()
    println("which builds licenseNode with metadata sum ${licenseNode.metadataSum()}")
    completed.add(licenseNode)
}

private fun step(
    currentBuilder: LicenseNodeBuilder,
    builderStack: ArrayDeque<LicenseNodeBuilder>,
    sequence: Iterator<Int>
): LicenseNodeBuilder {
    if (!sequence.hasNext()) {
        return currentBuilder
    }

//    println("taking step with builder $currentBuilder")

    when {
        currentBuilder.requiresChildrenCount() -> {
            currentBuilder.addChildrenCount(sequence.next())
            return currentBuilder
        }
        currentBuilder.requiresMetadataCount() -> {
            currentBuilder.addMetadataCount(sequence.next())
            return currentBuilder
        }
        currentBuilder.requiresChildrenProcessing() -> {
            currentBuilder.addChildProcessing()
            builderStack.push(currentBuilder)

            val childBuilder = LicenseNodeBuilder()
            return step(childBuilder, builderStack, sequence)
        }
        else -> {
            currentBuilder.addMetadata(sequence.next())
            return currentBuilder
        }
    }
}

class LicenseFile(private val nodes: List<LicenseNode>) {
    fun sumMetadata(): Int {
        return nodes.sumBy { it.metadataSum() }
    }

}
