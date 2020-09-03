package calculator

class Calculator {
    companion object {
        private lateinit var uInput: String
        private lateinit var convertedInput: MutableList<String>
        private var isInputError = false

        fun start(str: String) {
            isInputError = false
            uInput = str
            checkForGeneralErrors()
            if (!isInputError) {
                dropUnnecessarySymbols()
            }
        }

        private fun dropUnnecessarySymbols() {
            var tempList = uInput.split(' ') as MutableList

            for (i in 0..tempList.lastIndex) {

                if (i % 2 == 1 && tempList[i].contains(("[+-]").toRegex()) &&
                        tempList[i].contains(("[^-+]").toRegex())) {
                    var res = tempList[i]

                    println("Before removal: $res")

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

                    println("After removal: $res")
                    tempList[i] = res
                }
            }
        }

        private fun checkForGeneralErrors() {
            var tempList: MutableList<String> =
                    uInput.split(' ') as MutableList<String>
            for (i in tempList.indices) {
                if (i % 2 == 0) {
                    if (tempList[i].toBigIntegerOrNull() == null) invalidExpressionAlert()
                } else {
                    //проверяем символы в нечетных элементах
                     println(tempList[i])
                    if (tempList[i].contains(("[^-*^+()]").toRegex())) invalidExpressionAlert()
                    //проверяем последовательность + и -
                    if (tempList[i].contains(("[+-]").toRegex()) &&
                            tempList[i].contains(("[^-+]").toRegex())) invalidExpressionAlert()

                }
            }
             println(tempList)

        }

        private fun invalidExpressionAlert() {
            println("Invalid expression")
            isInputError = true
        }

    }
}