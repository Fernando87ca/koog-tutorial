package com.koog.example.file_creator_test

import kotlin.math.sqrt

class Calculator {
    fun add(a: Double, b: Double): Double {
        return a + b
    }

    fun subtract(a: Double, b: Double): Double {
        return a - b
    }

    fun multiply(a: Double, b: Double): Double {
        return a * b
    }

    fun divide(a: Double, b: Double): Double {
        require(b != 0.0) { "Divisor cannot be zero" }
        return a / b
    }

    fun squareRoot(a: Double): Double {
        require(a >= 0.0) { "Cannot calculate square root of a negative number" }
        return sqrt(a)
    }
}