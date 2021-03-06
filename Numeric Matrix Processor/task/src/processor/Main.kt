package processor

import java.util.Scanner
import kotlin.math.roundToLong

/**
 * Runs the program loop, displaying the main menu and calling the appropriate matrix function each time round.
 */
fun main() {
    val scanner = Scanner(System.`in`)
    do {
        when (inputAction(scanner)) {
            1 -> addMatrices(scanner)
            2 -> multiplyMatrixByConstant(scanner)
            3 -> multiplyMatrices(scanner)
            4 -> transposeMatrix(scanner)
            5 -> calculateDeterminant(scanner)
            6 -> inverseMatrix(scanner)
            0 -> return
        }
    } while (true)
}

fun inputAction(scanner: Scanner): Int {
    println("1. Add matrices")
    println("2. Multiply matrix to a constant")
    println("3. Multiply matrices")
    println("4. Transpose matrix")
    println("5. Calculate a determinant")
    println("6. Inverse matrix")
    println("0. Exit")
    print("Your choice: ")
    val choice = scanner.nextInt()
    scanner.nextLine()
    return choice
}

// The following functions each implement one of the choices from the main menu. They are responsible for reading
// input parameters from the console, creating the corresponding Matrix objects, calling the appropriate function
// of the matrix object and printing the resulting matrix or scalar value.

const val CONSTANT_PROMPT = "Enter constant: "

const val SIZE_PROMPT = "Enter size of matrix: "
const val SIZE_PROMPT_FIRST = "Enter size of first matrix: "
const val SIZE_PROMPT_SECOND = "Enter size of second matrix: "

const val ELEMENTS_PROMPT = "Enter matrix:"
const val ELEMENTS_PROMPT_FIRST = "Enter first matrix:"
const val ELEMENTS_PROMPT_SECOND = "Enter second matrix:"

const val RESULT_INTRO = "The result is:"
const val ADDITION_RESULT_INTRO = "The addition result is:"
const val MULTIPLICATION_RESULT_INTRO = "The multiplication result is:"

fun addMatrices(scanner: Scanner) {
    val matA = readMatrix(scanner, SIZE_PROMPT_FIRST, ELEMENTS_PROMPT_FIRST)
    val matB = readMatrix(scanner, SIZE_PROMPT_SECOND, ELEMENTS_PROMPT_SECOND)
    println(ADDITION_RESULT_INTRO)
    println(matA + matB)
}

fun multiplyMatrixByConstant(scanner: Scanner) {
    val matrix = readMatrix(scanner, SIZE_PROMPT, ELEMENTS_PROMPT)
    print(CONSTANT_PROMPT)
    val constant = scanner.nextDouble(); scanner.nextLine()
    println(MULTIPLICATION_RESULT_INTRO)
    println(matrix * constant)
}

fun multiplyMatrices(scanner: Scanner) {
    val matA = readMatrix(scanner, SIZE_PROMPT_FIRST, ELEMENTS_PROMPT_FIRST)
    val matB = readMatrix(scanner, SIZE_PROMPT_SECOND, ELEMENTS_PROMPT_SECOND)
    println(MULTIPLICATION_RESULT_INTRO)
    println(matA * matB)
}

fun transposeMatrix(scanner: Scanner) {
    val variant = inputVariant(scanner)
    val matrix = readMatrix(scanner, SIZE_PROMPT, ELEMENTS_PROMPT)
    println(RESULT_INTRO)
    println(matrix.transpose(variant))
}

fun inputVariant(scanner: Scanner): Int {
    println()
    println("1. Main diagonal")
    println("2. Side diagonal")
    println("3. Vertical line")
    println("4. Horizontal line")
    print("Your choice: ")
    val choice = scanner.nextInt()
    scanner.nextLine()
    return choice
}

fun calculateDeterminant(scanner: Scanner) {
    val matrix = readMatrix(scanner, SIZE_PROMPT, ELEMENTS_PROMPT)
    println(RESULT_INTRO)
    println(matrix.determinant())
    println()
}

fun inverseMatrix(scanner: Scanner) {
    val matrix = readMatrix(scanner, SIZE_PROMPT, ELEMENTS_PROMPT)
    println(RESULT_INTRO)
    println(matrix.inverse())
    println()
}

/**
 * Creates and returns a matrix based on the data read from the input [scanner] that is entered in response to the
 * specified prompts for the size of the matrix and its individual elements.
 */
fun readMatrix(scanner: Scanner, sizePrompt: String, elementsPrompt: String): Matrix {
    print(sizePrompt)
    val rows = scanner.nextInt()
    val cols = scanner.nextInt()
    println(elementsPrompt)
    val elements = Array(rows) { DoubleArray(cols) }
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            elements[row][col] = scanner.nextDouble()
        }
        scanner.nextLine()
    }
    return Matrix(rows, cols, elements)
}

/**
 * Represents a matrix. Provides a constructor to create a matrix with the specified numbers of [rows] and [cols]
 * and, optionally, an Array of DoubleArrays specifying its [elements]. The elements are otherwise set to 0.
 * Provides functions to perform various matrix operations and to output a string representation of the matrix.
 */
class Matrix(
        private val rows: Int,
        private val cols: Int,
        private val elements: Array<DoubleArray> = Array(rows) { DoubleArray(cols) }
    ) {

    override fun toString(): String {
        var result = ""
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result += "%5.2f ".format((elements[row][col] * 100).roundToLong() / 100.0)
            }
            result += "\n"
        }
        return result
    }

    /**
     * Adds this matrix by the specified [other] matrix.
     */
    operator fun plus(other: Matrix): Matrix {
        val result = Matrix(rows, cols)
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result.elements[row][col] = this.elements[row][col] + other.elements[row][col]
            }
        }
        return result
    }

    /**
     * Multiplies this matrix by the specified [scalar] value.
     */
    operator fun times(scalar: Double): Matrix {
        val result = Matrix(rows, cols)
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result.elements[row][col] = this.elements[row][col] * scalar
            }
        }
        return result
    }

    /**
     * Multiplies this matrix by the specified [other] matrix.
     */
    operator fun times(other: Matrix): Matrix {
        val result = Matrix(this.rows, other.cols)
        for (row in 0 until result.rows) {
            for (col in 0 until result.cols) {
                for (i in 0 until this.cols) {
                    result.elements[row][col] += this.elements[row][i] * other.elements[i][col]
                }
            }
        }
        return result
    }

    fun transpose(type: Int): Matrix {
        val result = Matrix(cols, rows)
        when (type) {
            1 -> mainDiagonal(result)
            2 -> sideDiagonal(result)
            3 -> verticalLine(result)
            4 -> horizontalLine(result)
        }
        return result
    }

    private fun mainDiagonal(result: Matrix) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result.elements[col][row] = elements[row][col]
            }
        }
    }

    private fun sideDiagonal(result: Matrix) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result.elements[cols - col - 1][rows - row - 1] = elements[row][col]
            }
        }
    }

    private fun verticalLine(result: Matrix) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result.elements[row][cols - col - 1] = elements[row][col]
            }
        }
    }

    private fun horizontalLine(result: Matrix) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                result.elements[rows - row - 1][col] = elements[row][col]
            }
        }
    }

    fun determinant(): Double {
        if (rows == 2) {
            return elements[0][0] * elements[1][1] - elements[0][1] * elements[1][0]
        }
        var result = 0.0
        for (col in 0 until cols) {
            result += elements[0][col] * cofactor(0, col)
        }
        return result
    }

    fun inverse(): Matrix {
        val cofactorMatrix = Matrix(rows, cols)
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                cofactorMatrix.elements[row][col] = cofactor(row, col)
            }
        }
        return cofactorMatrix.transpose(1) * (1 / determinant())
    }

    private fun cofactor(i: Int, j: Int): Double {
        val submatrix = Matrix(rows - 1, cols - 1)
        for (row in 0 until submatrix.rows) {
            for (col in 0 until submatrix.cols) {
                submatrix.elements[row][col] = elements[row + if (row >= i) 1 else 0][col + if (col >= j) 1 else 0]
            }
        }
        return submatrix.determinant() * if ((i + j) % 2 == 0) 1.0 else -1.0
    }
}
