package calculator

import java.math.BigInteger

class Calculator {
    companion object {
        private lateinit var uInput: String
        private var convertedInput = MutableList(0) { "" }
        private var listOfOperations = MutableList(0) { 0 }
        private lateinit var finalResult: BigInteger
        private var isInputError = false

        fun start(str: String) {
            convertedInput.clear()
            listOfOperations.clear()
            finalResult = 0.toBigInteger()
            isInputError = false
            uInput = str

            checkForGeneralErrors()
            if (!isInputError) {
                findOperations()
                calculate()
                println(finalResult)
            } else println("Invalid expression")

        }

        private fun calculate() {
            var temporalResult = 0.toBigInteger()
            if (listOfOperations.isNotEmpty()) {
                while (listOfOperations.size > 0) {
                    val firstOperationPlace = listOfOperations[0]
                    println(convertedInput)
                    temporalResult = getSimpleResult(convertedInput[firstOperationPlace],
                            convertedInput[firstOperationPlace - 1].toBigInteger(),
                            convertedInput[firstOperationPlace + 1].toBigInteger())
                    listOfOperations.removeAt(0)
                    convertedInput.add(firstOperationPlace - 1, temporalResult.toString())


                    repeat(3) { convertedInput.removeAt(firstOperationPlace) }
                    println("После шага вычислений и удаления отработанной операции: " + convertedInput)
                    for (i in listOfOperations.indices) {
                        if (listOfOperations[i] > firstOperationPlace) listOfOperations[i] = listOfOperations[i] - 2
                    }
                }
            }
            finalResult = convertedInput[0].toBigInteger()
        }

        private fun getSimpleResult(str: String, a: BigInteger, b: BigInteger): BigInteger {
            var res = 0.toBigInteger()
            when (str) {
                "*" -> res = a * b
                "^" -> res = a.pow(b.toInt())
                "/" -> {
                    if (b != 0.toBigInteger()) res = a / b else println("Деление на нулл")
                }
                "+" -> res = a + b
                "-" -> res = a - b
            }
            return res
        }

        private fun findOperations() {

            // println("первый элемент" + convertedInput[0].toBigInteger())
            // println("размер convertedinput" + convertedInput.size)
            if (convertedInput.size == 1) finalResult = convertedInput[0].toBigInteger() else {
                //try to find multiplication, dividing and power
                for (i in convertedInput.lastIndex - 1 downTo 1 step 2) {
                    if (convertedInput[i].contains(("[*^/]").toRegex())) {
                        listOfOperations.add(i)
                    }
                }
                for (i in convertedInput.lastIndex - 1 downTo 1 step 2) {
                    if (convertedInput[i].contains(("[-+]").toRegex())) {
                        listOfOperations.add(i)
                    }
                }
                println(listOfOperations)
                replaceMinus()
            }
        }

        private fun replaceMinus() {
            for (i in convertedInput.lastIndex - 1 downTo 1 step 2) {
                if (convertedInput[i] == "-") {
                    convertedInput[i] = "+"
                    convertedInput[i + 1] = "-" + convertedInput[i + 1]
                }
            }
        }

        private fun dropUnnecessarySymbols(i: Int) {
            var tempList = uInput.split(' ') as MutableList
            var res = tempList[i]

            //println("Before removal: $res")
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

                }

            }

            //println("After removal: $res")
            convertedInput[i] = res
            // println(convertedInput)


        }

        private fun checkForGeneralErrors() {
            var tempList: MutableList<String> =
                    uInput.split(' ') as MutableList<String>
            convertedInput = MutableList(tempList.size) { "" }
            for (i in tempList.indices) {
                convertedInput[i] = tempList[i]
                if (i % 2 == 0) {
                    if (tempList[i].toBigIntegerOrNull() == null) invalidExpressionAlert()
                } else {
                    //проверяем символы в нечетных элементах
                    //    println(tempList[i])
                    if (tempList[i].contains(("[^-*^+()/]").toRegex())) invalidExpressionAlert()
                    //проверяем последовательность + и -
                    if (tempList[i].contains(("[+-]").toRegex())) {
                        if (tempList[i].contains(("[^-+]").toRegex())) invalidExpressionAlert() else {
                            dropUnnecessarySymbols(i)
                        }
                    }


                }
            }
            // println(tempList)

        }

        private fun invalidExpressionAlert() {
            //println("Invalid expression")
            isInputError = true
        }

    }
}