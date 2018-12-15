package com.dambra.adventofcode2018.day13

import com.dambra.adventofcode2018.repeat

data class Location(val x: Int, val y: Int)

class Cart(
    var direction: String,
    var location: Location
) {
    var crashed = false

    fun move(rails: Map<Location, String>): Cart {
        location = when (direction) {
            "^" -> location.copy(y = location.y - 1)
            ">" -> location.copy(x = location.x + 1)
            "v" -> location.copy(y = location.y + 1)
            "<" -> location.copy(x = location.x - 1)
            else -> throw Exception("unknown direction: $direction")
        }
        return checkForTurn(rails[location]!!)
    }

    private fun left(s: String) = when (s) {
        "<" -> "v"
        "^" -> "<"
        ">" -> "^"
        "v" -> ">"
        else -> throw Exception("unknown cart direction: $s")
    }

    private fun right(s: String) = when (s) {
        "<" -> "^"
        "^" -> ">"
        ">" -> "v"
        "v" -> "<"
        else -> throw Exception("unknown cart direction: $s")
    }

    private fun checkForTurn(rail: String): Cart {
        return when {
            "|-".contains(rail) -> this
            rail == "+" -> atIntersection()
            rail == "/" -> when (direction) {
                "<" -> this.turnLeft()
                "^" -> this.turnRight()
                ">" -> this.turnLeft()
                "v" -> this.turnRight()
                else -> throw Exception("unknown direction: $direction")
            }
            rail == "\\" -> when (direction) {
                "<" -> this.turnRight()
                "^" -> this.turnLeft()
                ">" -> this.turnRight()
                "v" -> this.turnLeft()
                else -> throw Exception("unknown direction: $direction")
            }
            else -> throw Exception("unknown rail type: $rail")
        }
    }

    private fun turnLeft(): Cart {
        this.direction = left(this.direction)
        return this
    }

    private fun turnRight(): Cart {
        this.direction = right(this.direction)
        return this
    }

    private var intersectionChoices = sequenceOf("l", "s", "r").repeat().iterator()
    /**
     * Each time a cart has the option to turn (by arriving at any intersection),
     *
     *  - it turns left the first time,
     *  - goes straight the second time,
     *  - turns right the third time,
     *
     * and then repeats those directions starting again with left the fourth time, straight the fifth time, and so on.
     *
     * This process is independent of the particular intersection at which the cart has arrived
     * That is, the cart has no per-intersection memory.
     */
    private fun atIntersection(): Cart {
        val choice = intersectionChoices.next()
        return when (choice) {
            "l" -> this.turnLeft()
            "r" -> this.turnRight()
            "s" -> this
            else -> throw Exception("unknown intersection choice: $choice")
        }
    }

    override fun toString() = "Cart(direction='$direction', location=$location"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cart

        if (direction != other.direction) return false
        if (location != other.location) return false

        return true
    }

    override fun hashCode(): Int {
        var result = direction.hashCode()
        result = 31 * result + location.hashCode()
        return result
    }
}