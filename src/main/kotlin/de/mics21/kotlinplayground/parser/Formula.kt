package de.mics21.kotlinplayground.parser

import de.lostmekka.kotlinplayground.calculator.EvaluateException

class Formula(private val operand1 : FormulaInterface, private val operand2 : FormulaInterface, private val op : Operation): FormulaInterface {
    override fun evaluate(): Double = when (op) {
        Operation.PLUS -> operand1.evaluate() + operand2.evaluate()
        Operation.MINUS -> operand1.evaluate() - operand2.evaluate()
        Operation.TIMES -> operand1.evaluate() * operand2.evaluate()
        Operation.DIVIDE -> division(operand1, operand2)
    }

    // #TODO: is there a more elegant way to do this directly in when?
    private fun division(operand1: FormulaInterface, operand2: FormulaInterface) : Double{
        if (operand2.evaluate() == 0.toDouble()) {
            throw EvaluateException("Division by zero")
        } else {
            return operand1.evaluate() / operand2.evaluate()
        }
    }

}
