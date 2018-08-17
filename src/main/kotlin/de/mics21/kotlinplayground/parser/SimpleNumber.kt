package de.mics21.kotlinplayground.parser

data class SimpleNumber(val value : Double) : FormulaInterface {

    override fun evaluate() = value
}
