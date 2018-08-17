package de.mics21.kotlinplayground.parser

class Formula(private val operand1 : FormulaInterface, private val operand2 : FormulaInterface, private val op : Operation): FormulaInterface {
    override fun evaluate(): Double = when (op) {
        Operation.PLUS -> operand1.evaluate() + operand2.evaluate()
        Operation.MINUS -> operand1.evaluate() - operand2.evaluate()
        Operation.TIMES -> operand1.evaluate() * operand2.evaluate()
        Operation.DIVIDE -> operand1.evaluate() / operand2.evaluate()
    }

}
