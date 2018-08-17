package de.lostmekka.kotlinplayground.calculator

import de.mics21.kotlinplayground.parser.Parser

class Calculator : ICalculator {
    override fun evaluate(formula: String): Double {
        return Parser(formula).parse().evaluate()
    }
}
