package minesweeper

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    print("How many mines do you want on the field? ")
    val mines = scanner.nextInt()
    val minesField = MinesField(mines)

    println(minesField.optimizedHint)
}
