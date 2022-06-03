package minesweeper

import java.util.*

class MinesField(mines: Int) {
    private var random = Random()
    private val minesField = Array(9) { CharArray(9) }

    init {
        for (chars in minesField) {
            Arrays.fill(chars, '.')
        }
        for (i in 0 until mines) {
            while (true) {
                val x = random.nextInt(9)
                val y = random.nextInt(9)
                if (minesField[x][y] == '.') {
                    minesField[x][y] = 'X'
                    break
                }
            }
        }
    }

    private fun minesFieldToBinary(): Array<IntArray> {
        val out = Array(9) { IntArray(9) }
        for (i in 0..8) {
            for (j in 0..8) {
                if (minesField[i][j] == 'X') out[i][j] = 1 else out[i][j] = 0
            }
        }
        return out
    }// last clm// fst clm//last row//fst rom// 4th Corner//3rd Corner//snd Corner

    //fst Corner
    private val hint: Array<CharArray>
        get() {
            val binaryField = minesFieldToBinary()
            val hints = Array(9) { CharArray(9) }
            for (i in 0..8) {
                for (j in 0..8) {
                    var x: Int
                    if (binaryField[i][j] == 1) {
                        hints[i][j] = 'X'
                    } else {
                        x = if (i == 0 && j == 0) { //fst Corner
                            (binaryField[i][j + 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                        } else if (i == 0 && j == 8) { //snd Corner
                            binaryField[i][j - 1] + binaryField[i + 1][j - 1] + binaryField[i + 1][j]
                        } else if (i == 8 && j == 0) { //3rd Corner
                            (binaryField[i - 1][j] + binaryField[i - 1][j + 1] + binaryField[i][j + 1])
                        } else if (i == 8 && j == 8) { // 4th Corner
                            (binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i][j - 1])
                        } else if (i == 0) { //fst rom
                            (binaryField[i][j - 1] + binaryField[i][j + 1] + binaryField[i + 1][j - 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                        } else if (i == 8) { //last row
                            (binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i - 1][j + 1] + binaryField[i][j - 1] + binaryField[i][j + 1])
                        } else if (j == 0) { // fst clm
                            (binaryField[i - 1][j] + binaryField[i - 1][j + 1] + binaryField[i][j + 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                        } else if (j == 8) { // last clm
                            (binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i][j - 1] + binaryField[i + 1][j - 1] + binaryField[i + 1][j])
                        } else {
                            (binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i - 1][j + 1] + binaryField[i][j - 1] + binaryField[i][j + 1] + binaryField[i + 1][j - 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                        }
                        if (x == 0) hints[i][j] = '.' else hints[i][j] = (x + 48).toChar()
                    }
                }
            }
            return hints
        }

    val optimizedHint: String
        get() {
            val out = StringBuilder()
            for (chars in hint) {
                out.append(String(chars))
                out.append('\n')
            }
            return out.toString().trim { it <= ' ' }
        }
}