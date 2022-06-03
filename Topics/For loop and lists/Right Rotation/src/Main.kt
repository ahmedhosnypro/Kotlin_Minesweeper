fun main() {
    val numbers = MutableList(readln().toInt()) { readln().toInt() }

    val rotation = readln().toInt()

    val result = numbers.toMutableList()

    for (i in 0..numbers.lastIndex) {
        if (i + rotation <= numbers.lastIndex) {
            result[i + rotation] = numbers[i]
        } else {
            result[(i + rotation) % numbers.size] = numbers[i]
        }
    }

    for (n in result) {
        print("$n ")
    }
}