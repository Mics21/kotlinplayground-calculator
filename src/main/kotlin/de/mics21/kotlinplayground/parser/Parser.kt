package de.mics21.kotlinplayground.parser

import de.lostmekka.kotlinplayground.calculator.ParseException
import java.util.regex.Pattern
import kotlin.math.max
import kotlin.math.min

class Parser(private val formula: String) {

    private val validOps = Operation.values().map { it.char }

    /**
     * #TODO:
     * 0. eliminate spaces ?
     * 1. look for brackets an parse content to formulas
     * 2. look for / and * as stronger binding an parse this to formulas
     * 3. parse rest to formulas
     * 4. be happy, look for improvements
     */

    fun parse(): FormulaInterface {
        val firstBracket = formula.indexOf("(")
        val lastBracket = formula.lastIndexOf(")")

        if (firstBracket >= 0 && lastBracket >= 0 && firstBracket < lastBracket) {
            // expression in brackets found
            TODO("Not implemented :-(")
        } else if (firstBracket < 0 && lastBracket < 0) {
            // correct expression without brackets
            return parseBracketFreeExpression(formula)
        } else {
            // some strange brackets
            throw ParseException("wrong brackets found")
        }
    }

    private fun parseBracketFreeExpression(expr: String): FormulaInterface {
        val splittedFormula = splitByOperators()
        val numbers = splittedFormula.first
        val ops = splittedFormula.second

        return when {
            numbers.size == 1 && ops.isEmpty() -> numbers[0]
            numbers.isNotEmpty() && ops.isNotEmpty() -> createFormulaExpression(numbers, ops)
            else -> throw ParseException("parse error")

        }
    }

    private fun createFormulaExpression(numbers: List<FormulaInterface>, ops: List<Operation>): FormulaInterface {
        val parseTreeList = numbers.toMutableList()
        val mutableOps = ops.toMutableList()
        println(parseTreeList)
        println(mutableOps)

        while (mutableOps.isNotEmpty()) {
            if (mutableOps.indexOf(Operation.TIMES) < 0 && mutableOps.indexOf(Operation.DIVIDE) < 0) {
                // parse from left to right
                val op1 = parseTreeList.removeAt(0)
                val op2 = parseTreeList.removeAt(0)
                val expr = Formula(op1, op2, mutableOps.removeAt(0))
                parseTreeList.add(0, expr)
            } else {
                if (mutableOps.indexOf(Operation.TIMES) >= 0 && mutableOps.indexOf(Operation.DIVIDE) >= 0) {
                    val opIndex = min(mutableOps.indexOf(Operation.TIMES), mutableOps.indexOf(Operation.DIVIDE))
                    val op1 = parseTreeList.removeAt(opIndex)
                    val op2 = parseTreeList.removeAt(opIndex)
                    val expr = Formula(op1, op2, mutableOps.removeAt(opIndex))
                    parseTreeList.add(opIndex, expr)
                } else {
                    val opIndex = max(mutableOps.indexOf(Operation.TIMES), mutableOps.indexOf(Operation.DIVIDE))
                    val op1 = parseTreeList.removeAt(opIndex)
                    val op2 = parseTreeList.removeAt(opIndex)
                    val expr = Formula(op1, op2, mutableOps.removeAt(opIndex))
                    parseTreeList.add(opIndex, expr)
                }

            }
        }
        if (parseTreeList.size == 1) {
            return parseTreeList.first()
        } else {
            throw ParseException("Error during parsing")
        }
    }

    private fun splitByOperators(): Pair<List<SimpleNumber>, List<Operation>> {
        val numbers: List<SimpleNumber>
        try {
            numbers = formula.split(Pattern.compile("([\\+|\\-|\\*|\\/])")).map { SimpleNumber(it.toDouble()) }
        } catch (e: NumberFormatException) {
            throw ParseException("error in parsing to a number", e)
        }

        // #TODO: not sure if this is the best way. feels somehow not really good
        val ops = formula.filter { validOps.contains(it) }.toList().map {
            when (it) {
                Operation.PLUS.char -> Operation.PLUS
                Operation.MINUS.char -> Operation.MINUS
                Operation.TIMES.char -> Operation.TIMES
                else -> Operation.DIVIDE

            }
        }
        return Pair(numbers, ops)
    }
}
