package com.dambra.adventofcode2018.day16

interface ElfsemblyInstruction {
    val inputOne: Int
    val inputTwo: Int
    val output: Int
}

data class Instruction(val opcode: Int, override val inputOne: Int, override val inputTwo: Int, override val output: Int) :
    ElfsemblyInstruction {


    companion object {
        fun from(s: String): Instruction {
            val (a, b, c, d) = s.split(" ").map { it.toInt() }
            return Instruction(a, b, c, d)
        }
    }
}

interface Operation {
    fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int>
}
/**
 * Addition:

addi (add immediate) stores into register C the result of adding register A and value B.
 */
class Addi : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] + instruction.inputTwo
            } else {
                r
            }
        }
    }
}

/**
 * Addition:

addr (add register) stores into register C the result of adding register A and register B.
 */
class Addr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] + registers[instruction.inputTwo]
            } else {
                r
            }
        }
    }
}

/**
 * Multiplication:

muli (multiply immediate) stores into register C the result of multiplying register A and value B.
 */
class Muli : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] * instruction.inputTwo
            } else {
                r
            }
        }
    }
}

/**
 * Multiplication:

mulr (multiply register) stores into register C the result of multiplying register A and register B.
 */
class Mulr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] * registers[instruction.inputTwo]
            } else {
                r
            }
        }
    }
}

/**
Bitwise AND:

banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
        */
class Banr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] and registers[instruction.inputTwo]
            } else {
                r
            }
        }
    }
}

/**
Bitwise AND:

bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
 */
class Bani : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] and instruction.inputTwo
            } else {
                r
            }
        }
    }
}

/**
Bitwise OR:

borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
        */
class Borr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] or registers[instruction.inputTwo]
            } else {
                r
            }
        }
    }
}

/**
Bitwise OR:

bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
 */
class Bori : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne] or instruction.inputTwo
            } else {
                r
            }
        }
    }
}

/**
Assignment:

setr (set register) copies the contents of register A into register C. (Input B is ignored.)
seti (set immediate) stores value A into register C. (Input B is ignored.)
        */
class Setr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                registers[instruction.inputOne]
            } else {
                r
            }
        }
    }
}

/**
Assignment:

setr (set register) copies the contents of register A into register C. (Input B is ignored.)
seti (set immediate) stores value A into register C. (Input B is ignored.)
 */
class Seti : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                instruction.inputOne
            } else {
                r
            }
        }
    }
}

/**
Greater-than testing:

gtir (greater-than immediate/register) sets register C to 1
if value A is greater than register B. Otherwise, register C is set to 0.

 */
class Gtir : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                if (instruction.inputOne > registers[instruction.inputTwo])
                    1
                else
                    0
            } else {
                r
            }
        }
    }
}

/**
Greater-than testing:

gtri (greater-than register/immediate) sets register C to 1
if register A is greater than value B. Otherwise, register C is set to 0.
 */
class Gtri : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                if (registers[instruction.inputOne] > instruction.inputTwo)
                    1
                else
                    0
            } else {
                r
            }
        }
    }
}

/**
Greater-than testing:

gtrr (greater-than register/register) sets register C to 1
if register A is greater than register B. Otherwise, register C is set to 0.
 */
class Gtrr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                if (registers[instruction.inputOne] > registers[instruction.inputTwo])
                    1
                else
                    0
            } else {
                r
            }
        }
    }
}

/**
 * ```

Equality testing:

eqir (equal immediate/register) sets register C to 1
if value A is equal to register B. Otherwise, register C is set to 0.
```

 */
class Eqir : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                if (instruction.inputOne == registers[instruction.inputTwo])
                    1
                else
                    0
            } else {
                r
            }
        }
    }
}

/**
 * ```

Equality testing:

eqri (equal register/immediate) sets register C to 1
if register A is equal to value B. Otherwise, register C is set to 0.
```

 */
class Eqri : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                if (registers[instruction.inputOne] == instruction.inputTwo)
                    1
                else
                    0
            } else {
                r
            }
        }
    }
}

/**
 * ```

Equality testing:

eqrr (equal register/register) sets register C to 1
if register A is equal to register B. Otherwise, register C is set to 0.
```

 */
class Eqrr : Operation {
    override fun applyTo(instruction: ElfsemblyInstruction, registers: List<Int>): List<Int> {
        return registers.mapIndexed { i, r ->
            if (i == instruction.output) {
                if (registers[instruction.inputOne] == registers[instruction.inputTwo])
                    1
                else
                    0
            } else {
                r
            }
        }
    }
}