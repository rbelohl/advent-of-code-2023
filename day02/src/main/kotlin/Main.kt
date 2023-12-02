import java.io.File

fun isGamePossible(text: String) : Boolean {
    val maxCount = mapOf("red" to 12, "green" to 13, "blue" to 14)
    val games = text.split(";")
    games.forEach {game ->
        val cubes = game.split(",")
        cubes.forEach {cube ->
            val s = cube.trim().split(" ")
            val count = s[0].toInt()
            val color = s[1]
            if (count > maxCount[color]!!) {
                return false
            }
        }
    }
    return true
}

fun getPower(text: String) : Long {
    val maxCount = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
    val games = text.split(";")
    games.forEach {game ->
        val cubes = game.split(",")
        cubes.forEach {cube ->
            val s = cube.trim().split(" ")
            val count = s[0].toInt()
            val color = s[1]
            if (count > maxCount[color]!!) {
                maxCount[color] = count
            }
        }
    }
    return maxCount.values.map { it.toLong() }.reduce{ a, b -> a * b}
}

fun main(args: Array<String>) {
    val filename = args[0]

    val games = File(filename).readLines()
        .map { it.split(":") }

    val result1 = games.filter { isGamePossible(it[1]) }
        .map { it[0] }
        .sumOf { it.split(" ")[1].toInt() }

    val result2 = games.sumOf { getPower(it[1]) }

    println(result1)
    println(result2)
}