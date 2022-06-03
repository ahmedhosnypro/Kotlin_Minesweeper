import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val numbers = MutableList(readln().toInt()) { readln().toInt() }

    if (scanner.nextInt() in numbers && scanner.nextInt() in numbers) {
        print("YES")
    } else {
        print("NO")
    }
}