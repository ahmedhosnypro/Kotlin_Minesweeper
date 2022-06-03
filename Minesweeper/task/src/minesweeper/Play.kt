package minesweeper

import java.util.*

object Play {
    private var scanner = Scanner(System.`in`)
    fun play(minesField: MinesField, mines: Int) {
        var markedEmptyCell = false
        var x = 0
        for (i in 0..8) {
            for (j in 0..8) {
                val c1: Char = minesField.getMinesField()[i][j]
                val c2: Char = minesField.getHiddenMinesField()[i][j]
                if (c1 == '.' && c2 == '*') {
                    markedEmptyCell = true
                    break
                } else if (c1 == 'X' && c2 == '*') {
                    x++
                }
            }
        }
        if (!markedEmptyCell && x == mines) {
            println("Congratulations! You found all mines!")
        } else {
            showHiddenMines(minesField, mines)
        }
    }

    private fun showHiddenMines(minesField: MinesField, mines: Int) {
        val xCord: Int
        val yCord: Int
        while (true) {
            val cords = coordinates
            if (cords[0] != -1 || cords[1] != -1) {
                xCord = cords[0]
                yCord = cords[1]
                break
            } else println("check input")
        }
        if (markCell(minesField.getHiddenMinesField(), yCord - 1, xCord - 1)) {
            println()
            println(minesField.optimizedHint)
        }
        play(minesField, mines)
    }

    private fun markCell(field: Array<CharArray>, xCord: Int, yCord: Int): Boolean {
        var isMarked = false
        val ch = field[xCord][yCord]
        if (ch.code in 48..57) {
            println("There is a number here!")
        } else if (ch == '*') {
            field[xCord][yCord] = '.'
            isMarked = true
        } else {
            field[xCord][yCord] = '*'
            isMarked = true
        }
        return isMarked
    }

    private val coordinates: IntArray
        get() {
            print("Set/delete mines marks (x and y coordinates): ")
            var xCord = -1
            var yCord = -1
            try {
                xCord = scanner.next().toInt()
                yCord = scanner.next().toInt()
            } catch (ignored: IllegalArgumentException) {
                // to do
            }
            if (xCord > 9 || yCord > 9) {
                xCord = -1
                yCord = -1
            }
            return intArrayOf(xCord, yCord)
        }
}