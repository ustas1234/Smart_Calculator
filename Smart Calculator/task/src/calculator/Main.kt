package calculator


import java.lang.Exception
import kotlin.system.exitProcess

class Calculator {
    companion object {
        private lateinit var cleanString: MutableList<String>
        var goodInput = true

        fun processInput(uInput: List<String>) {
            val tempStroke = MutableList(uInput.size) { "" }
            for (i in uInput.indices) {
                if (uInput[i].toIntOrNull() == null) {
                    tempStroke[i] = getResultOperator(uInput[i])
                } else tempStroke[i] = uInput[i]
            }

            checkIsCorrectInput(tempStroke)
            if (goodInput) cleanString = tempStroke

        }

        private fun checkIsCorrectInput(stroke: MutableList<String>) {
            goodInput = true
            if (stroke[0].toIntOrNull() == null || stroke[stroke.lastIndex].toIntOrNull() == null) goodInput = false
            if (stroke.size % 2 == 0) goodInput = false
            for (i in stroke.indices){
                if (i % 2 == 0) {
                    if (stroke[i].toIntOrNull() == null) goodInput = false
                } else {
                    when (stroke[i]) {
                        "+","-","*" -> {

                        }
                        else -> goodInput = false
                    }
                }
            }
            if (!goodInput) println("Invalid expression")
        }

        private fun getResultOperator(s: String): String {
            var res = s
            while (res.lastIndex > 0) {

                when (res[0]) {
                    '+' -> {
                        res = res.drop(1)
                    }
                    '-' -> {
                        when (res[1]) {
                            '+' -> res = '-' + res.drop(2)
                            '-' -> res = '+' + res.drop(2)
                        }
                    }
                    /*else -> {
                        throw Exception("Found not +- characters")
                    }*/
                }

            }

            return res
        }

        fun goAction() {
            var tempResult = cleanString[0].toInt()
            for (i in cleanString.indices) {
                if (i == 1) {
                    when (cleanString[i]) {
                        "+" -> {
                            tempResult = cleanString[i - 1].toInt() + cleanString[i + 1].toInt()
                        }
                        "-" -> {
                            tempResult = cleanString[i - 1].toInt() - cleanString[i + 1].toInt()
                        }
                    }
                } else {
                    when (cleanString[i]) {
                        "+" -> {
                            tempResult += cleanString[i + 1].toInt()
                        }
                        "-" -> {
                            tempResult -= cleanString[i + 1].toInt()
                        }
                    }
                }
            }
            println(tempResult)
        }


    }
}

fun main() {
    var uInput: List<String>


    mainloop@ do {
        uInput = readLine()!!.split(' ')

        if (uInput[0].isEmpty()) continue

        if (uInput[0][0] == '/') {
            when (uInput[0]) {
                "/exit" -> exit()
                "/help" -> {
                    help()
                    continue@mainloop
                }
                else -> println("Unknown command")
            }

        } else {
            Calculator.processInput(uInput)
            if (Calculator.goodInput) Calculator.goAction()
        }


    } while (true)
}

fun help() {
    println("The program allows you to perform plus and minus operations of various length")
}

fun exit() {
    println("Bye!")
    exitProcess(-1)
}
