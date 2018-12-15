package com.dambra.adventofcode2018.day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Recipes {

    @Test
    fun `add the recipes and then move the elves`() {
        val recipesState = RecipeTracker()
        recipesState.step()

        assertThat(recipesState.recipes).isEqualTo(listOf(3, 7, 1, 0))
        assertThat(recipesState.elfOne).isEqualTo(0)
        assertThat(recipesState.elfTwo).isEqualTo(1)
    }

    @Test
    fun `step twice`() {
        // 3  7  1 [0](1) 0

        val recipesState = RecipeTracker()
        recipesState.step()
        recipesState.step()

        assertThat(recipesState.recipes).isEqualTo(listOf(3, 7, 1, 0, 1, 0))
        assertThat(recipesState.elfOne).isEqualTo(4)
        assertThat(recipesState.elfTwo).isEqualTo(3)
    }

    @Test
    fun `step five times`() {
        // 3  7  1  0 (1) 0  1  2 [4]

        val recipesState = RecipeTracker()
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
        val recipesState = RecipeTracker()

        val nextTen = recipesState.tenScoresAfter(9)
        assertThat(nextTen.joinToString("")).isEqualTo("5158916779")
    }

    @Test
    fun `after 5 recipes, the scores of the next ten would be 0124515891`() {
        val recipesState = RecipeTracker()

        val nextTen = recipesState.tenScoresAfter(5)
        assertThat(nextTen.joinToString("")).isEqualTo("0124515891")
    }

    @Test
    fun `after 18 recipes, the scores of the next ten would be 9251071085`() {
        val recipesState = RecipeTracker()

        val nextTen = recipesState.tenScoresAfter(18)
        assertThat(nextTen.joinToString("")).isEqualTo("9251071085")
    }

    @Test
    fun `after 2018 recipes, the scores of the next ten would be 5941429882`() {
        val recipesState = RecipeTracker()

        val nextTen = recipesState.tenScoresAfter(2018)
        assertThat(nextTen.joinToString("")).isEqualTo("5941429882")
    }

    @Test
    fun `after puzzle input recipes, what are the next ten recipes`() {
        val recipesState = RecipeTracker()

        val nextTen = recipesState.tenScoresAfter(327901)
        assertThat(nextTen.joinToString("")).isEqualTo("1115317115")
    }

    @Test
    fun `elves want to seek for 51589 recipe sequences`() {
        val recipesState = RecipeTracker()
        val numberOfRecipesUntil = recipesState.seekFor("51589")
        assertThat(numberOfRecipesUntil).isEqualTo(9)
    }

    @Test
    fun `elves want to seek for 01245 recipe sequences`() {
        val recipesState = RecipeTracker()
        val numberOfRecipesUntil = recipesState.seekFor("01245")
        assertThat(numberOfRecipesUntil).isEqualTo(5)
    }

    @Test
    fun `elves want to seek for 92510 recipe sequences`() {
        val recipesState = RecipeTracker()
        val numberOfRecipesUntil = recipesState.seekFor("92510")
        assertThat(numberOfRecipesUntil).isEqualTo(18)
    }

    @Test
    fun `elves want to seek for 59414 recipe sequences`() {
        val recipesState = RecipeTracker()
        val numberOfRecipesUntil = recipesState.seekFor("59414")
        assertThat(numberOfRecipesUntil).isEqualTo(2018)
    }

    @Test
    fun `elves want to seek for puzzle input recipe sequences`() {
        val recipesState = RecipeTracker()
        val numberOfRecipesUntil = recipesState.seekFor("327901")
        assertThat(numberOfRecipesUntil).isLessThan(53274238)
        assertThat(numberOfRecipesUntil).isLessThan(20229823)
        assertThat(numberOfRecipesUntil).isEqualTo(20229822)
    }
}