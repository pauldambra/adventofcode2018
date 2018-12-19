package com.dambra.adventofcode2018.day18

data class Acre(val type: String) {

    fun tick(surroundings: Map<String, Int>): Acre {
        return when (type) {
            openGround -> if (surroundings.getOrDefault(trees, 0) >= 3) {
                this.copy(type = trees)
            } else {
                this.copy()
            }
            trees -> if (surroundings.getOrDefault(lumberyard, 0) >= 3) {
                this.copy(type = lumberyard)
            } else {
                this.copy()
            }
            lumberyard -> if (
                surroundings.getOrDefault(lumberyard, 0) >= 1 &&
                surroundings.getOrDefault(trees, 0) >= 1
            ) {
                this.copy()
            } else {
                this.copy(type = openGround)
            }
            else -> throw Exception("there is no type of acre: $type")
        }
    }

    companion object {
        const val openGround = "."
        const val trees = "|"
        const val lumberyard = "#"
    }
}