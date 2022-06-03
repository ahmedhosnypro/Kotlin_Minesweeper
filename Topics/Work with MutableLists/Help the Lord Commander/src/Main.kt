fun main() {
    val beyondTheWall = readLine()!!.split(", ").map { it }.toMutableList()
    val backToTheWall = readLine()!!.split(", ").map { it }.toMutableList()

    var allInPatrol = true
    for (g in beyondTheWall) {
        if (g !in backToTheWall) {
            allInPatrol = false
            break
        }
    }

    print(allInPatrol)
}