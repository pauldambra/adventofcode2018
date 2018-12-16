package com.dambra.adventofcode2018.day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ParsingTheMap {
    @Test
    fun `can parse the example map`() {
        val s = """
#########
#G..G..G#
#.......#
#.......#
#G..E..G#
#.......#
#.......#
#G..G..G#
#########
        """.trimIndent()
        val map = BattleMap(s)

        assertThat(map.elves).containsExactly(Elf(4, 4))
        assertThat(map.goblins).containsExactly(
            Goblin(1, 1),
            Goblin(4, 1),
            Goblin(7, 1),
            Goblin(1, 4),
            Goblin(7, 4),
            Goblin(1, 7),
            Goblin(4, 7),
            Goblin(7, 7)
        )
        assertThat(map.walls).containsExactly(
            Wall(0, 0),
            Wall(1, 0),
            Wall(2, 0),
            Wall(3, 0),
            Wall(4, 0),
            Wall(5, 0),
            Wall(6, 0),
            Wall(7, 0),
            Wall(8, 0),
            Wall(0, 1),
            Wall(8, 1),
            Wall(0, 2),
            Wall(8, 2),
            Wall(0, 3),
            Wall(8, 3),
            Wall(0, 4),
            Wall(8, 4),
            Wall(0, 5),
            Wall(8, 5),
            Wall(0, 6),
            Wall(8, 6),
            Wall(0, 7),
            Wall(8, 7),
            Wall(0, 8),
            Wall(1, 8),
            Wall(2, 8),
            Wall(3, 8),
            Wall(4, 8),
            Wall(5, 8),
            Wall(6, 8),
            Wall(7, 8),
            Wall(8, 8)
        )
    }
}
