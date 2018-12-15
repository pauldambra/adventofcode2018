package com.dambra.adventofcode2018.day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecipesState {
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

internal class Recipes {

    @Test
    fun `add the recipes and then move the elves`() {
        val recipesState = RecipesState()
        recipesState.step()

        assertThat(recipesState.recipes).isEqualTo(listOf(3, 7, 1, 0))
        assertThat(recipesState.elfOne).isEqualTo(0)
        assertThat(recipesState.elfTwo).isEqualTo(1)
    }

    @Test
    fun `step twice`() {
        // 3  7  1 [0](1) 0

        val recipesState = RecipesState()
        recipesState.step()
        recipesState.step()

        assertThat(recipesState.recipes).isEqualTo(listOf(3, 7, 1, 0, 1, 0))
        assertThat(recipesState.elfOne).isEqualTo(4)
        assertThat(recipesState.elfTwo).isEqualTo(3)
    }

    @Test
    fun `step five times`() {
        // 3  7  1  0 (1) 0  1  2 [4]

        val recipesState = RecipesState()
        recipesState.step()
        recipesState.step()
        recipesState.step()
        recipesState.step()
        recipesState.step()

        assertThat(recipesState.recipes).isEqualTo(listOf(3, 7, 1, 0, 1, 0, 1, 2, 4))
        assertThat(recipesState.elfOne).isEqualTo(4)
        assertThat(recipesState.elfTwo).isEqualTo(8)
    }

    @Test
    fun `after nine steps what are the next ten scores`() {
        // 3  7  1  0 [1] 0  1  2 (4) 5  1  5  8  9  1  6  7  7  9
        val recipesState = RecipesState()

        val nextTen = recipesState.tenScoresAfter(9)
        assertThat(nextTen.joinToString("")).isEqualTo("5158916779")
    }

    @Test
    fun `after 5 recipes, the scores of the next ten would be 0124515891`() {
        val recipesState = RecipesState()

        val nextTen = recipesState.tenScoresAfter(5)
        assertThat(nextTen.joinToString("")).isEqualTo("0124515891")
    }

    @Test
    fun `after 18 recipes, the scores of the next ten would be 9251071085`() {
        val recipesState = RecipesState()

        val nextTen = recipesState.tenScoresAfter(18)
        assertThat(nextTen.joinToString("")).isEqualTo("9251071085")
    }

    @Test
    fun `after 2018 recipes, the scores of the next ten would be 5941429882`() {
        val recipesState = RecipesState()

        val nextTen = recipesState.tenScoresAfter(2018)
        assertThat(nextTen.joinToString("")).isEqualTo("5941429882")
    }

    @Test
    fun `after puzzle input recipes, what are the next ten recipes`() {
        val recipesState = RecipesState()

        val nextTen = recipesState.tenScoresAfter(327901)
        assertThat(nextTen.joinToString("")).isEqualTo("1115317115")
    }

    @Test
    fun `elves want to seek for 51589 recipe sequences`() {
        val recipesState = RecipesState()
        val numberOfRecipesUntil = recipesState.seekFor("51589")
        assertThat(numberOfRecipesUntil).isEqualTo(9)
    }

    @Test
    fun `elves want to seek for 01245 recipe sequences`() {
        val recipesState = RecipesState()
        val numberOfRecipesUntil = recipesState.seekFor("01245")
        assertThat(numberOfRecipesUntil).isEqualTo(5)
    }

    @Test
    fun `elves want to seek for 92510 recipe sequences`() {
        val recipesState = RecipesState()
        val numberOfRecipesUntil = recipesState.seekFor("92510")
        assertThat(numberOfRecipesUntil).isEqualTo(18)
    }

    @Test
    fun `elves want to seek for 59414 recipe sequences`() {
        val recipesState = RecipesState()
        val numberOfRecipesUntil = recipesState.seekFor("59414")
        assertThat(numberOfRecipesUntil).isEqualTo(2018)
    }

    @Test
    fun `elves want to seek for puzzle input recipe sequences`() {
        val recipesState = RecipesState()
        val numberOfRecipesUntil = recipesState.seekFor("327901")
        assertThat(numberOfRecipesUntil).isLessThan(53274238)
        assertThat(numberOfRecipesUntil).isLessThan(20229823)
        assertThat(numberOfRecipesUntil).isEqualTo(20229822)
    }
}