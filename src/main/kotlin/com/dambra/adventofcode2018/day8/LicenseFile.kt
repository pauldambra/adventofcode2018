package com.dambra.adventofcode2018.day8

import java.util.*

class LicenseFile(private val rootNode: LicenseNode, private val nodes: List<LicenseNode>) {

    companion object {

        private val nodeRelationships: MutableMap<LicenseNode, MutableSet<LicenseNode>> = mutableMapOf()

        internal fun parse(s: String): LicenseFile {
            // first char is children count
            // next character is items count
            // remaining chars are either the children or the items

            val completed = mutableListOf<LicenseNode>()

            val builderStack = ArrayDeque<LicenseNode>()
            builderStack.push(LicenseNode())

            val i = s.split(" ")
                .asSequence()
                .map { it.toInt() }
                .iterator()

            var currentBuilder = LicenseNode()
            val rootNode = currentBuilder

            nodeRelationships.putIfAbsent(currentBuilder, mutableSetOf())

            while (i.hasNext()) {

                currentBuilder = if (currentBuilder.isComplete()) {
                    completed.add(currentBuilder)
                    builderStack.pop()
                } else {
                    step(currentBuilder, builderStack, i)
                }
                nodeRelationships.putIfAbsent(currentBuilder, mutableSetOf())
            }
            completed.add(currentBuilder)

            return LicenseFile(rootNode, completed.toList())
        }

        private fun step(
            currentBuilder: LicenseNode,
            builderStack: ArrayDeque<LicenseNode>,
            sequence: Iterator<Int>
        ): LicenseNode {
            require(sequence.hasNext()) { "input sequence iterator must have another item to read" }

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

                    val childBuilder = LicenseNode()

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

    fun sumMetadata() = nodes.sumBy { it.metadata.sum() }

    fun rootNodeValue() = findNodeValue(rootNode, nodeRelationships[rootNode]!!).sum()

    private fun findNodeValue(currentNode: LicenseNode, children: MutableSet<LicenseNode>): List<Int> =
        if (children.isEmpty()) {
            useTheSumOfTheMetadata(currentNode)
        } else {
            currentNode.metadata
                .map { metadata ->
                    useMetadataAsIndexIntoValueOfChildren(metadata, children)
                }
                .flatten()
        }

    private fun useMetadataAsIndexIntoValueOfChildren(
        metadata: Int,
        children: MutableSet<LicenseNode>
    ): List<Int> {
        val matchedChild = children.elementAtOrNull(metadata - 1)

        return if (matchedChild == null) {
            listOf(0)
        } else {
            findNodeValue(matchedChild, nodeRelationships[matchedChild]!!)
        }
    }

    private fun useTheSumOfTheMetadata(currentNode: LicenseNode) =
        listOf(currentNode.metadata.sum())

}