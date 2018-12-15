package com.dambra.adventofcode2018.day13

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class MovingAndTurning {
    @Test
    fun `can go north`() {
        val c = Cart("^", Location(2, 4))
        val rails = mapOf(
            Location(2, 3) to "|"
        )
        Assertions.assertThat(c.move(rails).location)
            .isEqualTo(Location(2, 3))
    }

    @Test
    fun `can go south`() {
        val c = Cart("v", Location(2, 4))
        val rails = mapOf(
            Location(2, 5) to "|"
        )
        Assertions.assertThat(c.move(rails).location)
            .isEqualTo(Location(2, 5))
    }

    @Test
    fun `can go east`() {
        val c = Cart(">", Location(2, 4))
        val rails = mapOf(
            Location(3, 4) to "-"
        )
        Assertions.assertThat(c.move(rails).location)
            .isEqualTo(Location(3, 4))
    }

    @Test
    fun `can go west`() {
        val c = Cart("<", Location(2, 4))
        val rails = mapOf(
            Location(1, 4) to "-"
        )
        Assertions.assertThat(c.move(rails).location)
            .isEqualTo(Location(1, 4))
    }

    @Test
    fun `ends up heading south when going west and hitting a left turn`() {
        val c = Cart("<", Location(2, 4))
        val rails = mapOf(
            Location(1, 4) to "/"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo("v")
    }

    @Test
    fun `ends up heading south when going east and hitting a right turn`() {
        val c = Cart(">", Location(2, 4))
        val rails = mapOf(
            Location(3, 4) to "\\"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo("v")
    }

    @Test
    fun `ends up heading north when going east and hitting a left turn`() {
        val c = Cart(">", Location(2, 4))
        val rails = mapOf(
            Location(3, 4) to "/"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo("^")
    }

    @Test
    fun `ends up heading north when going west and hitting a right turn`() {
        val c = Cart("<", Location(2, 4))
        val rails = mapOf(
            Location(1, 4) to "\\"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo("^")
    }

    @Test
    fun `ends up heading east when going south and hitting a left turn`() {
        val c = Cart("v", Location(2, 4))
        val rails = mapOf(
            Location(2, 5) to "\\"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo(">")
    }

    @Test
    fun `ends up heading east when going north and hitting a right turn`() {
        val c = Cart("^", Location(2, 4))
        val rails = mapOf(
            Location(2, 3) to "/"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo(">")
    }

    @Test
    fun `ends up heading west when going south and hitting a right turn`() {
        val c = Cart("v", Location(2, 4))
        val rails = mapOf(
            Location(2, 5) to "/"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo("<")
    }

    @Test
    fun `ends up heading west when going north and hitting a left turn`() {
        val c = Cart("^", Location(2, 4))
        val rails = mapOf(
            Location(2, 3) to "\\"
        )
        val x = c.move(rails)
        Assertions.assertThat(x.direction).isEqualTo("<")
    }
}