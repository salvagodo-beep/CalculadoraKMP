package com.example.calculadorakmp

import kotlin.math.*

class Calculadora {

    var pantalla = "0"
        private set

    var operacion = ""
        private set

    private var primerNumero = 0.0
    private var operador = ""
    private var nuevoNumero = true

    fun pulsar(tecla: String) {

        when (tecla) {

            in "0123456789" -> {
                if (nuevoNumero || pantalla == "0") {
                    pantalla = tecla
                    nuevoNumero = false

                    if (operador.isEmpty()) {
                        operacion = ""
                    }
                } else {
                    pantalla += tecla
                }
            }

            ",", "." -> {
                if (nuevoNumero) {
                    pantalla = "0."
                    nuevoNumero = false
                } else if (!pantalla.contains(".")) {
                    pantalla += "."
                }
            }

            "C" -> {
                pantalla = "0"
                operacion = ""
                primerNumero = 0.0
                operador = ""
                nuevoNumero = true
            }

            "⌫" -> {
                pantalla = if (pantalla.length > 1) {
                    pantalla.dropLast(1)
                } else {
                    "0"
                }
            }

            "+", "−", "×", "÷", "mod", "xʸ" -> {
                primerNumero = pantalla.toDoubleOrNull() ?: 0.0
                operador = tecla
                operacion = "$pantalla $tecla"
                nuevoNumero = true
            }

            "=" -> {
                if (operador.isNotEmpty()) {
                    operacion = "$operacion $pantalla"
                }
                calcular()
            }

            "+/−" -> {
                operacion = "−($pantalla)"
                pantalla = formato(-(pantalla.toDoubleOrNull() ?: 0.0))
                nuevoNumero = true
            }

            "√x" -> aplicarFuncion("√") { sqrt(it) }
            "x²" -> aplicarFuncion("²") { it * it }
            "1/x" -> aplicarFuncion("1/") { 1.0 / it }
            "|x|" -> aplicarFuncion("abs") { abs(it) }
            "10ˣ" -> aplicarFuncion("10^") { 10.0.pow(it) }
            "exp" -> aplicarFuncion("exp") { exp(it) }
            "log" -> aplicarFuncion("log") { log10(it) }
            "ln" -> aplicarFuncion("ln") { ln(it) }
            "sin" -> aplicarFuncion("sin") {
                sin(it * PI / 180.0)
            }
            "cos" -> aplicarFuncion("cos") {
                cos(it * PI / 180.0)
            }
            "tan" -> aplicarFuncion("tan") {
                tan(it * PI / 180.0)
            }
            "n!" -> aplicarFuncion("!") { factorial(it) }
        }
    }

    private fun calcular() {

        val segundoNumero = pantalla.toDoubleOrNull() ?: 0.0

        val resultado = when (operador) {
            "+" -> primerNumero + segundoNumero
            "−" -> primerNumero - segundoNumero
            "×" -> primerNumero * segundoNumero
            "÷" -> if (segundoNumero != 0.0) {
                primerNumero / segundoNumero
            } else null

            "mod" -> if (segundoNumero != 0.0) {
                primerNumero % segundoNumero
            } else null

            "xʸ" -> primerNumero.pow(segundoNumero)

            else -> segundoNumero
        }

        pantalla = resultado?.let { formato(it) } ?: "Error"

        operador = ""
        nuevoNumero = true
    }

    private fun aplicarFuncion(
        nombre: String,
        funcion: (Double) -> Double
    ) {
        val numero = pantalla.toDoubleOrNull() ?: return

        operacion = "$nombre($pantalla)"

        val resultado = funcion(numero)

        pantalla = if (resultado.isFinite()) {
            formato(resultado)
        } else {
            "Error"
        }

        nuevoNumero = true
    }

    private fun factorial(numero: Double): Double {

        if (numero < 0 || numero % 1.0 != 0.0 || numero > 170) {
            return Double.NaN
        }

        var resultado = 1.0

        for (i in 2..numero.toInt()) {
            resultado *= i
        }

        return resultado
    }

    private fun formato(numero: Double): String {
        return if (numero % 1.0 == 0.0) {
            numero.toLong().toString()
        } else {
            numero.toString()
        }
    }
}
