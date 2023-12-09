import java.io.File

fun <T> infiniteRepeat(sequence: Sequence<T>) = sequence { while (true) yieldAll(sequence) }

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()
    val directions = infiniteRepeat(lines[0].asSequence())


    val graph = lines.drop(2)
        .map { line ->
            line.split(' ', '=', '(', ',', ')').filter { it.isNotBlank() }
        }.associate { it[0] to mapOf('L' to it[1], 'R' to it[2]) }

    var current = "AAA"
    val destination = "ZZZ"
    var steps = 0
    for (c in directions) {
        println(current)
        current = graph[current]!![c]!!
        steps++
        if (current == destination) {
            break
        }
    }

    println(steps)
}