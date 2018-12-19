package com.dambra.adventofcode2018.day18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class Acres {
    @Test
    fun `trees can become lumberyards`() {
        val a = Acre("|")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 3
            )
        )
        Assertions.assertThat(b)
            .isEqualTo(Acre(Acre.lumberyard))
    }

    @Test
    fun `trees can stay trees`() {
        val a = Acre("|")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 2
            )
        )
        Assertions.assertThat(b)
            .isEqualTo(Acre(Acre.trees))
    }

    @Test
    fun `lumberyards can become open`() {
        val a = Acre("#")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 3
            )
        )
        Assertions.assertThat(b)
            .isEqualTo(Acre(Acre.openGround))
    }

    @Test
    fun `lumberyards can stay lumberyards`() {
        val a = Acre("#")
        val b = a.tick(
            mapOf(
                Acre.lumberyard to 1,
                Acre.trees to 1
            )
        )
        Assertions.assertThat(b)
            .isEqualTo(Acre(Acre.lumberyard))
    }

    @Test
    fun `open ground can become trees`() {
        val a = Acre(".")
        val b = a.tick(
            mapOf(
                Acre.trees to 3
            )
        )
        Assertions.assertThat(b)
            .isEqualTo(Acre(Acre.trees))
    }

    @Test
    fun `open ground can stay open`() {
        val a = Acre(".")
        val b = a.tick(
            mapOf(
                Acre.trees to 2
            )
        )
        Assertions.assertThat(b)
            .isEqualTo(Acre(Acre.openGround))
    }
}