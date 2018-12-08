package com.dambra.adventofcode2018.day8

class LicenseNode {
    private var processedChildren: Int = 0

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

    val metadata: MutableList<Int> = mutableListOf()

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
}