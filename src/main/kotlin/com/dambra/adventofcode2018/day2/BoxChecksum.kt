package com.dambra.adventofcode2018.day2

fun List<LetterFrequency>.boxChecksum(): Int {

    var twos = 0
    var threes = 0

    this.forEach {
        if (it.exactlyTwoTimesLetter.isNotEmpty()) twos++
        if (it.exactlyThreeTimesLetter.isNotEmpty()) threes++
    }

    return twos * threes
}

data class Comparison(val left: String, val right: String, val numberOfMatchingCharacters: Int) {
    // doesn't matter what order left and right are when compared
    override fun equals(other: Any?): Boolean {
        if (other != null && other is Comparison) {
            return other.numberOfMatchingCharacters == this.numberOfMatchingCharacters
                    &&
                    (other.left == this.left || other.left == this.right)
                    &&
                    (other.right == this.left || other.right == this.right)
        }

        return false
    }

    override fun hashCode(): Int {
        val result = (left + right).toSortedSet().hashCode()
        return 31 * result + numberOfMatchingCharacters
    }
}

fun List<String>.similarIds(): List<String> {

    val targetMatchingCharacters = this.first().length - 1

    val comparisons = mutableSetOf<Comparison>()

    this.forEachIndexed { leftIndex, left ->
        this.forEachIndexed { rightIndex, right ->
            if (leftIndex != rightIndex) {
                val matches = left.zip(right)
                    .filter { it -> it.first == it.second }
                    .count()

                if (matches == targetMatchingCharacters) {
                    comparisons.add(Comparison(left, right, matches))
                }
            }
        }
    }

    return comparisons
        .map {
            it.left.zip(it.right)
                .fold("") { acc, pair ->
                    if (pair.first == pair.second) {
                        acc + pair.first.toString()
                    } else {
                        acc
                    }
                }
        }

}