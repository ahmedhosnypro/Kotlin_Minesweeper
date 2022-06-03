package minesweeper

import java.util.*

///create non calling methods
object Play {
    private var scanner = Scanner(System.`in`)
     fun play(minesField: MinesField) {
        if (isAllFree(minesField) || isOnlyMinesMarked(minesField)) {
            println("Congratulations! You found all the mines!")
        } else {
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
            when (scanner.next()) {
                "free" -> free(minesField, xCord, yCord)
                "mine" -> mine(minesField, xCord, yCord)
                else -> println("check input")
            }
        }
    }

    private fun isAllFree(minesField: MinesField): Boolean {
        var isAllFree = true
        for (i in 0..8) {
            for (j in 0..8) {
                val explored = minesField.exploredField[i][j]
                val original = minesField.minesField[i][j]
                if (original != '.' || explored != '/' && (explored.code < 48 || explored.code > 57)
                    && (original != 'X' || explored != '.')
                ) {
                    isAllFree = false
                    break
                }
            }
        }
        return isAllFree
    }

    private fun isOnlyMinesMarked(minesField: MinesField): Boolean {
        var isOnlyMinesMarked = true
        for (i in 0..8) {
            for (j in 0..8) {
                val explored = minesField.exploredField[i][j]
                val original = minesField.minesField[i][j]
                if ((original != 'X' || explored != '*') && (original == 'X' || explored == '*')) {
                    isOnlyMinesMarked = false
                }
            }
        }
        return isOnlyMinesMarked
    }

    private fun free(minesField: MinesField, xCord: Int, yCord: Int) {
        if (isMine(minesField, xCord, yCord)) {
            for (i in 0..8) {
                for (j in 0..8) {
                    if (minesField.minesField[i][j] == 'X') minesField.exploredField[i][j] = 'X'
                }
            }
            println()
            println(minesField.optimizedExploredField)
            println("You stepped on a mine and failed!")
        } else if (isEmptyAndMinesAround(minesField, xCord, yCord)) {
            minesField.exploredField[xCord][yCord] = minesField.hiddenMinesField[xCord][yCord]
            println()
            println(minesField.optimizedExploredField)
            play(minesField)
        } else if (isEmptyAndNoMinesAround(minesField, xCord, yCord)) {
            minesField.exploredField[xCord][yCord] = '/'
            freeCell1(minesField, xCord, yCord)
            println()
            println(minesField.optimizedExploredField)
            play(minesField)
        }
    }

    private fun mine(minesField: MinesField, xCord: Int, yCord: Int) {
        if (isMarked(minesField, xCord, yCord)) {
            minesField.markedMines[xCord][yCord] = '.'
            minesField.exploredField[xCord][yCord] = '.'
        } else {
            minesField.markedMines[xCord][yCord] = '*'
            minesField.exploredField[xCord][yCord] = '*'
        }
        println()
        println(minesField.optimizedExploredField)
        play(minesField)
    }

    private fun freeCell1(minesField: MinesField, xCord: Int, yCord: Int) {
        freeCellNorth(minesField, xCord, yCord)
        freeCellSouth(minesField, xCord, yCord)
        freeCellEast(minesField, xCord, yCord)
        freeCellWest(minesField, xCord, yCord)
        for (i in 0..80) {
            patch(minesField)
        }

//        freeCellNorth_East(minesField, xCord, yCord);
//        freeCellNorth_West(minesField, xCord, yCord);
//        freeCellSouth_East(minesField, xCord, yCord);
//        freeCellSouth_West(minesField, xCord, yCord);
    }

    private fun patchFreeTool(minesField: MinesField, xCord: Int, yCord: Int) {
        if (isEmptyAndMinesAround(minesField, xCord, yCord)) {
            minesField.exploredField[xCord][yCord] = minesField.hiddenMinesField[xCord][yCord]
        } else if (isEmptyAndNoMinesAround(minesField, xCord, yCord)) {
            minesField.exploredField[xCord][yCord] = '/'
        }
    }

    private fun patch(minesField: MinesField) {
        for (i in 0..8) {
            for (j in 0..8) {
                val notExplored = minesField.exploredField[i][j]
                if (notExplored == '.' || notExplored == '*') {
                    var north: Char
                    var south: Char
                    var east: Char
                    var west: Char
                    var northEast: Char
                    var northWest: Char
                    var southEast: Char
                    var southWest: Char
                    if (i == 0 && j == 0) { //fst Corner
                        south = minesField.exploredField[i + 1][j]
                        east = minesField.exploredField[i][j + 1]
                        southEast = minesField.exploredField[i + 1][j + 1]
                        if (south == '/' || east == '/' || southEast == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (i == 0 && j == 8) { //snd Corner
                        south = minesField.exploredField[i + 1][j]
                        west = minesField.exploredField[i][j - 1]
                        southWest = minesField.exploredField[i + 1][j - 1]
                        if (south == '/' || west == '/' || southWest == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (i == 8 && j == 0) { //3rd corner
                        north = minesField.exploredField[i - 1][j]
                        east = minesField.exploredField[i][j + 1]
                        northEast = minesField.exploredField[i - 1][j + 1]
                        if (north == '/' || east == '/' || northEast == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (i == 8 && j == 8) { //4th corner
                        north = minesField.exploredField[i - 1][j]
                        west = minesField.exploredField[i][j - 1]
                        northWest = minesField.exploredField[i - 1][j - 1]
                        if (north == '/' || west == '/' || northWest == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (i == 0) { //fst Row
                        south = minesField.exploredField[i + 1][j]
                        east = minesField.exploredField[i][j + 1]
                        southEast = minesField.exploredField[i + 1][j + 1]
                        west = minesField.exploredField[i][j - 1]
                        southWest = minesField.exploredField[i + 1][j - 1]
                        if (south == '/' || east == '/' || southEast == '/' || southWest == '/' || west == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (i == 8) { //last Row
                        east = minesField.exploredField[i][j + 1]
                        west = minesField.exploredField[i][j - 1]
                        north = minesField.exploredField[i - 1][j]
                        northEast = minesField.exploredField[i - 1][j + 1]
                        northWest = minesField.exploredField[i - 1][j - 1]
                        if (northWest == '/' || northEast == '/' || north == '/' || west == '/' || east == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (j == 0) { //fst Clm
                        east = minesField.exploredField[i][j + 1]
                        north = minesField.exploredField[i - 1][j]
                        northEast = minesField.exploredField[i - 1][j + 1]
                        south = minesField.exploredField[i + 1][j]
                        southEast = minesField.exploredField[i + 1][j + 1]
                        if (southEast == '/' || south == '/' || northEast == '/' || north == '/' || east == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else if (j == 8) { //last Clm
                        north = minesField.exploredField[i - 1][j]
                        south = minesField.exploredField[i + 1][j]
                        west = minesField.exploredField[i][j - 1]
                        northWest = minesField.exploredField[i - 1][j - 1]
                        southWest = minesField.exploredField[i + 1][j - 1]
                        if (southWest == '/' || northWest == '/' || west == '/' || south == '/' || north == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    } else {
                        north = minesField.exploredField[i - 1][j]
                        south = minesField.exploredField[i + 1][j]
                        east = minesField.exploredField[i][j + 1]
                        west = minesField.exploredField[i][j - 1]
                        northEast = minesField.exploredField[i - 1][j + 1]
                        northWest = minesField.exploredField[i - 1][j - 1]
                        southEast = minesField.exploredField[i + 1][j + 1]
                        southWest = minesField.exploredField[i + 1][j - 1]
                        if (southWest == '/' || southEast == '/' || northWest == '/' || northEast == '/' || west == '/' || east == '/' || south == '/' || north == '/') {
                            patchFreeTool(minesField, i, j)
                        }
                    }
                }
            }
        }
    }

    private fun isEmptyAndNoMinesAround(minesField: MinesField, xCord: Int, yCord: Int): Boolean {
        val n = minesField.binaryField[xCord][yCord]
        val ch = minesField.hiddenMinesField[xCord][yCord].code
        return n == 0 && ch == '.'.code
    }

    private fun isEmptyAndMinesAround(minesField: MinesField, xCord: Int, yCord: Int): Boolean {
        val n = minesField.binaryField[xCord][yCord]
        val ch = minesField.hiddenMinesField[xCord][yCord].code
        return n == 0 && ch >= 48 && ch <= 57
    }

    private fun isMine(minesField: MinesField, xCord: Int, yCord: Int): Boolean {
        val n = minesField.binaryField[xCord][yCord]
        return n == 1
    }

    private fun isMarked(minesField: MinesField, xCord: Int, yCord: Int): Boolean {
        return minesField.markedMines[xCord][yCord] == '*'
    }

    private fun freeCellNorth(minesField: MinesField, x: Int, y: Int) {
        var xCord = x
        while (xCord > 0) {
            if (isEmptyAndMinesAround(minesField, xCord - 1, y)) {
                minesField.exploredField[xCord - 1][y] = minesField.hiddenMinesField[xCord - 1][y]
                break
            } else if (isEmptyAndNoMinesAround(minesField, xCord - 1, y)) {
                minesField.exploredField[xCord - 1][y] = '/'
                xCord--
            }
            //East
            var east = y
            while (east < 8) {
                if (isEmptyAndMinesAround(minesField, xCord, east + 1)) {
                    minesField.exploredField[xCord][east + 1] = minesField.hiddenMinesField[xCord][east + 1]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, xCord, east + 1)) {
                    minesField.exploredField[xCord][east + 1] = '/'
                    east++
                }
            }
            //West
            var west = y
            while (west > 0) {
                if (isEmptyAndMinesAround(minesField, xCord, west - 1)) {
                    minesField.exploredField[xCord][west - 1] = minesField.hiddenMinesField[xCord][west - 1]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, xCord, west - 1)) {
                    minesField.exploredField[xCord][west - 1] = '/'
                    west--
                }
            }
        }
    }

    private fun freeCellSouth(minesField: MinesField, x: Int, y: Int) {
        var xCord = x
        while (xCord < 8) {
            if (isEmptyAndMinesAround(minesField, xCord + 1, y)) {
                minesField.exploredField[xCord + 1][y] = minesField.hiddenMinesField[xCord + 1][y]
                break
            } else if (isEmptyAndNoMinesAround(minesField, xCord + 1, y)) {
                minesField.exploredField[xCord + 1][y] = '/'
                xCord++
            }
            //East
            var east = y
            while (east < 8) {
                if (isEmptyAndMinesAround(minesField, xCord, east + 1)) {
                    minesField.exploredField[xCord][east + 1] = minesField.hiddenMinesField[xCord][east + 1]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, xCord, east + 1)) {
                    minesField.exploredField[xCord][east + 1] = '/'
                    east++
                }
            }
            //West
            var west = y
            while (west > 0) {
                if (isEmptyAndMinesAround(minesField, xCord, west - 1)) {
                    minesField.exploredField[xCord][west - 1] = minesField.hiddenMinesField[xCord][west - 1]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, xCord, west - 1)) {
                    minesField.exploredField[xCord][west - 1] = '/'
                    west--
                }
            }
        }
    }

    private fun freeCellEast(minesField: MinesField, x: Int, y: Int) {
        var yCord = y
        while (yCord < 8) {
            if (isEmptyAndMinesAround(minesField, x, yCord + 1)) {
                minesField.exploredField[x][yCord + 1] = minesField.hiddenMinesField[x][yCord + 1]
                break
            } else if (isEmptyAndNoMinesAround(minesField, x, yCord + 1)) {
                minesField.exploredField[x][yCord + 1] = '/'
                yCord++
            }
            //North
            var north = x
            while (north > 0) {
                if (isEmptyAndMinesAround(minesField, north - 1, yCord)) {
                    minesField.exploredField[north - 1][yCord] = minesField.hiddenMinesField[north - 1][yCord]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, north - 1, yCord)) {
                    minesField.exploredField[north - 1][yCord] = '/'
                    north--
                }
            }
            //South
            var south = x
            while (south < 8) {
                if (isEmptyAndMinesAround(minesField, south + 1, yCord)) {
                    minesField.exploredField[south + 1][yCord] = minesField.hiddenMinesField[south + 1][yCord]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, south + 1, yCord)) {
                    minesField.exploredField[south + 1][yCord] = '/'
                    south++
                }
            }
        }
    }

    private fun freeCellWest(minesField: MinesField, x: Int, y: Int) {
        var yCord = y
        while (yCord > 0) {
            if (isEmptyAndMinesAround(minesField, x, yCord - 1)) {
                minesField.exploredField[x][yCord - 1] = minesField.hiddenMinesField[x][yCord - 1]
                break
            } else if (isEmptyAndNoMinesAround(minesField, x, yCord - 1)) {
                minesField.exploredField[x][yCord - 1] = '/'
                yCord--
            }
            //North
            var north = x
            while (north > 0) {
                if (isEmptyAndMinesAround(minesField, north - 1, yCord)) {
                    minesField.exploredField[north - 1][yCord] = minesField.hiddenMinesField[north - 1][yCord]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, north - 1, yCord)) {
                    minesField.exploredField[north - 1][yCord] = '/'
                    north--
                }
            }
            //South
            var south = x
            while (south < 8) {
                if (isEmptyAndMinesAround(minesField, south + 1, yCord)) {
                    minesField.exploredField[south + 1][yCord] = minesField.hiddenMinesField[south + 1][yCord]
                    break
                } else if (isEmptyAndNoMinesAround(minesField, south + 1, yCord)) {
                    minesField.exploredField[south + 1][yCord] = '/'
                    south++
                }
            }
        }
    }

    private val coordinates: IntArray
        get() {
            print("Set/unset mines marks or claim a cell as free: ")
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
            return intArrayOf(yCord - 1, xCord - 1)
        }
    //    private static boolean isValidCoordinates(int xCord, int yCord) {
    //        return (xCord >= 0 && xCord <= 8) && (yCord >= 0 && yCord <= 8);
    //    }
}