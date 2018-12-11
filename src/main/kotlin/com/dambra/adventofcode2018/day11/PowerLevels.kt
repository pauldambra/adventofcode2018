package com.dambra.adventofcode2018.day11

data class Cell(val x: Int, val y: Int)

/**
 * For example, to find the power level of the fuel cell at 3,5 in a grid with serial number 8:
 *
 * The rack ID is 3 + 10 = 13.
 * The power level starts at 13 * 5 = 65.
 * Adding the serial number produces 65 + 8 = 73.
 * Multiplying by the rack ID produces 73 * 13 = 949.
 * The hundreds digit of 949 is 9.
 * Subtracting 5 produces 9 - 5 = 4.
 */
fun powerLevelOf(cell: Cell, serial: Int): Int {
    val rackId = cell.x + 10
    val rackSerialPowerLevel = (rackId * cell.y + serial) * rackId
    val digits = rackSerialPowerLevel
        .toString()
        .reversed()
        .split("")
        .filterNot { x: String -> x == "" }
        .map { it.toInt() }

    val hundredsDigit= digits[2]
    return hundredsDigit - 5
}
