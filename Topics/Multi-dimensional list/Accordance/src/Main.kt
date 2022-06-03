fun main() {
    val matrix = ArrayList<ArrayList<String>>()

    for (i in 0..1) {
        val tmpMatrix = ArrayList<String>()
        for (j in 0..2) {
            tmpMatrix.add("[${i}][${j}]")
        }
        matrix.add(tmpMatrix)
    }

    print(matrix)
}