package com.dambra.adventofcode2018.day14

class RecipeTracker {
    val recipes = mutableListOf(3, 7)

    var elfOne = 0
    var elfTwo = 1

    fun step(): List<Int> {
        val newRecipes = (recipes[elfOne] + recipes[elfTwo])
            .toString()
            .split("")
            .dropLast(1)
            .drop(1)
            .map(String::toInt)

        recipes.addAll(newRecipes)

        elfOne = (elfOne + (1 + recipes[elfOne])) % recipes.size
        elfTwo = (elfTwo + (1 + recipes[elfTwo])) % recipes.size

        return newRecipes
    }

    private fun stepUntilThereAreThisManyRecipes(i: Int) {
        while (recipes.size < i) {
            step()
        }
    }

    fun tenScoresAfter(i: Int): MutableList<Int> {
        stepUntilThereAreThisManyRecipes(i + 11)
        return recipes.subList(i, i + 10)
    }

    fun seekFor(s: String): Int {
        val target = s.split("").drop(1).dropLast(1).map(String::toInt)

        var foundTarget: Pair<Boolean, Int>?
        do {
            val justAdded = step()
            foundTarget = recipesEndsWith(target, justAdded)
        } while (!foundTarget!!.first)

        return foundTarget.second
    }

    private fun recipesEndsWith(
        target: List<Int>,
        justAdded: List<Int>
    ): Pair<Boolean, Int> {
        val targetCouldFitInRecipe = recipes.size >= target.size
        if (!targetCouldFitInRecipe) return Pair(false, -1)

        val lastPossibleStartIndex = recipes.size - target.size
        val end = recipes.size

        //if we add more than one number the target might not be at the very end of the list
        for (i in 0..justAdded.size) {
            val from = lastPossibleStartIndex - i
            val to = end - i
            if (from < 0 || to < 0) continue

            if (recipes.subList(from, to) == target) {
                return Pair(true, from)
            }
        }
        return Pair(false, -1)
    }
}