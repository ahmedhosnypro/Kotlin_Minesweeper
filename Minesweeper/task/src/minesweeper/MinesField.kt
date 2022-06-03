package minesweeper

import java.util.*

class MinesField(mines: Int) {
    private var random = Random()
    val minesField = Array(9) { CharArray(9) }
    var binaryField = Array(9) { IntArray(9) }
    val hiddenMinesField = Array(9) { CharArray(9) }
    val exploredField = Array(9) { CharArray(9) }
    val markedMines = Array(9) { CharArray(9) }

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

        //binaryField
        for (i in 0..8) {
            for (j in 0..8) {
                if (minesField[i][j] == 'X') binaryField[i][j] = 1 else binaryField[i][j] = 0
            }
        }

        //playable Field
        //marked mines
        for (i in 0..8) {
            for (j in 0..8) {
                exploredField[i][j] = '.'
                markedMines[i][j] = '.'
            }
        }


        //hiddenMinesField
        for (i in 0..8) {
            for (j in 0..8) {
                var x: Int
                if (binaryField[i][j] == 1) {
                    hiddenMinesField[i][j] = '.'
                } else {
                    if (i == 0 && j == 0) { //fst Corner
                        x = (binaryField[i][j + 1]
                                + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                    } else if (i == 0 && j == 8) { //snd Corner
                        x = binaryField[i][j - 1] +
                                binaryField[i + 1][j - 1] + binaryField[i + 1][j]
                    } else if (i == 8 && j == 0) { //3rd Corner
                        x = (binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j + 1])
                    } else if (i == 8 && j == 8) { // 4th Corner
                        x = (binaryField[i - 1][j - 1] + binaryField[i - 1][j]
                                + binaryField[i][j - 1])
                    } else if (i == 0) { //fst rom
                        x = (binaryField[i][j - 1] + binaryField[i][j + 1]
                                + binaryField[i + 1][j - 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                    } else if (i == 8) { //last xCord
                        x = (binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j - 1] + binaryField[i][j + 1])
                    } else if (j == 0) { // fst clm
                        x = (binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j + 1]
                                + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                    } else if (j == 8) { // last clm
                        x = (binaryField[i - 1][j - 1] + binaryField[i - 1][j]
                                + binaryField[i][j - 1]
                                + binaryField[i + 1][j - 1] + binaryField[i + 1][j])
                    } else {
                        x = (binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j - 1] + binaryField[i][j + 1]
                                + binaryField[i + 1][j - 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1])
                    }
                    if (x == 0) hiddenMinesField[i][j] = '.' else hiddenMinesField[i][j] = (x + 48).toChar()
                }
            }
        }
    }

    val optimizedExploredField: String
        get() {
            val out = StringBuilder()
            out.append(
                """
                 |123456789|
                -|---------|
                
            """.trimIndent()
            )
            var i = 1
            for (chars in exploredField) {
                out.append(i).append('|')
                out.append(String(chars)).append('|').append('\n')
                i++
            }
            out.append("-|---------|")
            return out.toString()
        }
}