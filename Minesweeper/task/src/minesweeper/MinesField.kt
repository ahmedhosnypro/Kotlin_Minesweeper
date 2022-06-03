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

    val optimizedMinesField: String
        get() {
            val out = StringBuilder()
            for (chars in minesField) {
                out.append(String(chars))
                out.append('\n')
            }
            return out.toString().trim { it <= ' ' }
        }
}