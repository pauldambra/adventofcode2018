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


data class LicenseNode(
    val childrenCount: Int,
    val metadataCount: Int,
    val metadata: List<Int>
) {
    fun metadataSum() = metadata.sum()
}

class LicenseNodeBuilder {
    private var processedChildren: Int = 0
    val metadata: MutableList<Int> = mutableListOf()

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

    fun build() = LicenseNode(childrenCount!!, metadataCount!!, metadata)

}

class LicenseFile(private val rootNode: LicenseNodeBuilder, private val nodes: List<LicenseNode>) {

    companion object {

        private val nodeRelationships: MutableMap<LicenseNodeBuilder, MutableSet<LicenseNodeBuilder>> = mutableMapOf()

        internal fun parse(s: String): LicenseFile {
            // first char is children count
            // next character is metadata count
            // remaining chars are either the children or the metadata

            val completed = mutableListOf<LicenseNode>()

            val builderStack = ArrayDeque<LicenseNodeBuilder>()
            builderStack.push(LicenseNodeBuilder())

            val i = s.split(" ")
                .asSequence()
                .map { it.toInt() }
                .iterator()

            var currentBuilder = LicenseNodeBuilder()
            val rootNode = currentBuilder

            nodeRelationships.putIfAbsent(currentBuilder, mutableSetOf())

            while (i.hasNext()) {

                currentBuilder = if (currentBuilder.isComplete()) {
                    completeNode(currentBuilder, completed)
                    builderStack.pop()
                } else {
                    step(currentBuilder, builderStack, i)
                }
                nodeRelationships.putIfAbsent(currentBuilder, mutableSetOf())
            }
            completeNode(currentBuilder, completed) //final node should be ready now
            return LicenseFile(rootNode, completed)
        }

        private fun completeNode(
            currentBuilder: LicenseNodeBuilder,
            completed: MutableList<LicenseNode>
        ) {
            val licenseNode = currentBuilder.build()
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

                    nodeRelationships[currentBuilder]!!.add(childBuilder)

                    return step(childBuilder, builderStack, sequence)
                }
                else -> {
                    currentBuilder.addMetadata(sequence.next())
                    return currentBuilder
                }
            }
        }
    }

    fun rootNodeValue(): Int {

        val x = nodeRelationships
            .map {
                it.key to findNodeValue(it.key, it.value)
            }
            .toMap()

        val xs = x[rootNode]!!

        return xs.sum()
    }

    private fun findNodeValue(
        currentNode: LicenseNodeBuilder,
        children: MutableSet<LicenseNodeBuilder>
    ): List<Int> {

        val nodeValue = if (children.isEmpty()) {
            listOf(currentNode.metadata.sum())
        } else {
            currentNode.metadata.map { metadata ->
                val matchedChild = children.elementAtOrNull(metadata - 1)

                val found = if (matchedChild == null) {
                    listOf(0)
                } else {
                    findNodeValue(matchedChild, nodeRelationships[matchedChild]!!)
                }
                found
            }.flatten()
        }
        return nodeValue
    }

    fun sumMetadata(): Int {
        return nodes.sumBy { it.metadataSum() }
    }

}
