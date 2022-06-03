package minesweeper

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val mines = scanner.nextInt()
    val minesField = MinesField(mines)
    println(minesField.optimizedMinesField)
}
