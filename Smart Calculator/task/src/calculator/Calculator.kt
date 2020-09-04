package calculator

import java.math.BigInteger

class Calculator {
    companion object {
        private lateinit var uInput: String
        private var convertedInput = MutableList(0) { "" }
        private var listOfOperations = mutableMapOf<Int, String>()
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
                tryToCalculate()
                println(finalResult)
            } else println("Invalid expression")

        }

        private fun tryToCalculate() {

            // println("первый элемент" + convertedInput[0].toBigInteger())
            // println("размер convertedinput" + convertedInput.size)
            if (convertedInput.size == 1) finalResult = convertedInput[0].toBigInteger() else {
                //try to find multiplication, dividing and power
                for (i in 1..convertedInput.lastIndex step 2) {
                    if (convertedInput[i].contains(("[*^/]").toRegex())) {
                        listOfOperations[i] = convertedInput[i]
                    }
                }
                for (i in 1..convertedInput.lastIndex step 2) {
                    if (convertedInput[i].contains(("[-+]").toRegex())) {
                        listOfOperations[i] = convertedInput[i]
                    }
                }
                println(listOfOperations)
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