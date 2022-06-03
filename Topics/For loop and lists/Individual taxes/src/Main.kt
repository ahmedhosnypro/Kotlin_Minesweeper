fun main() {
    val incomes = MutableList(readln().toInt()) { readln().toInt() }
    val taxes = MutableList(incomes.size) { readln().toInt() }

    var hIndex = -1
    var hTax = Int.MIN_VALUE
    for (i in 0..incomes.lastIndex) {
        if (incomes[i] * taxes[i] > hTax) {
            hIndex = i
            hTax = incomes[i] * taxes[i]
        }
    }

    print(hIndex + 1)
}