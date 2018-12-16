package com.dambra.adventofcode2018.day15

data class Elf(val x: Int, val y: Int)
data class Goblin(val x: Int, val y: Int)
data class Wall(val x: Int, val y: Int)

class BattleMap(mapString: String) {
    val elves: MutableList<Elf> = mutableListOf()
    val goblins: MutableList<Goblin> = mutableListOf()
    val walls: MutableList<Wall> = mutableListOf()

    init {
        mapString.split("\n").forEachIndexed { y, row ->
            row.split("").drop(1).dropLast(1).forEachIndexed { x, c ->
                if(x==8 && y==7) println(c)
                if (c == "E") {
                    elves.add(Elf(x, y))
                }
                if (c == "G") {
                    goblins.add(Goblin(x, y))
                }
                if (c == "#") {
                    walls.add(Wall(x, y))
                }
            }
        }
    }
}