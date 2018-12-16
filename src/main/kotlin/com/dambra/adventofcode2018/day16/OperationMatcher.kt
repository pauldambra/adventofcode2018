package com.dambra.adventofcode2018.day16

data class Match(val instruction: Instruction, val operation: String, val matchedSample: Sample)

class OperationMatcher {
    val operations: Map<String, Operation> = mapOf(
        "addi" to Addi(),
        "addr" to Addr(),
        "mulr" to Mulr(),
        "muli" to Muli(),
        "banr" to Banr(),
        "bani" to Bani(),
        "borr" to Borr(),
        "bori" to Bori(),
        "setr" to Setr(),
        "seti" to Seti(),
        "gtir" to Gtir(),
        "gtri" to Gtri(),
        "gtrr" to Gtrr(),
        "eqir" to Eqir(),
        "eqri" to Eqri(),
        "eqrr" to Eqrr()
    )

    fun matchSample(sample: Sample): List<Match> = operations
        .entries
        .toList()
        .fold(emptyList()) { acc, operation ->
            if (operation.value.applyTo(sample.instruction, sample.registersBefore) == sample.registersAfter) {
                acc + Match(sample.instruction, operation.key, sample)
            } else {
                acc
            }
        }

    fun matchSample(s: String)
            = matchSample(Sample.parse(s))


}