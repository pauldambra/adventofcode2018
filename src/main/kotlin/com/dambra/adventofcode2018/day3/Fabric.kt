package com.dambra.adventofcode2018.day3

class Fabric(claims: List<String>) {
    private var claims: List<Claim> = claims.map { Claim.parse(it) }

    fun findOverlapArea(): Int {
        val countOfClaimsWithCoordinate = HashMap<Coordinate, Int>()
        claims.forEach { c ->
            c.coordinates.forEach {
                countOfClaimsWithCoordinate[it] = countOfClaimsWithCoordinate.getOrDefault(it, 0) + 1
            }
        }

        return countOfClaimsWithCoordinate.filter { it.value > 1 }.size
    }

    fun findIntactClaims(): List<Int> {
        val x = HashMap<Coordinate, List<Int>>()
        val overlappingClaims = mutableSetOf<Int>()

        claims.forEach { c ->
            c.coordinates.forEach {
                val newClaimsAtThisCoordinate = x.getOrDefault(it, emptyList()) + c.id
                x[it] = newClaimsAtThisCoordinate
                if (newClaimsAtThisCoordinate.size > 1) {
                    overlappingClaims.addAll(newClaimsAtThisCoordinate)
                }
            }
        }

        return claims
            .map {it.id}
            .filterNot { overlappingClaims.contains(it) }
    }
}



