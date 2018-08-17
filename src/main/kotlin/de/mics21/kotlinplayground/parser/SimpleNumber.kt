package de.mics21.kotlinplayground.parser

class SimpleNumber(val value : Double) : FormulaInterface {

    override fun evaluate() = value
}
