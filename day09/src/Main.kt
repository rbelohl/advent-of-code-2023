import java.io.File

fun findNext(sequence: List<Int>) : Int {
    val differences = mutableListOf(sequence.toMutableList())

    for (i in 0..sequence.size) {
        val difference = differences[i].windowed(2).map { it[1] - it[0] }
        differences.add(difference.toMutableList())
        if (difference.all { it == 0 }) {
            break
        }
    }

    differences.reverse()
    differences[0].add(0)

    for (i in 1..< differences.size) {
        val next = differences[i].last() + differences[i - 1].last()
        differences[i].add(next)
    }

    return differences.last().last()
}

fun main(args: Array<String>) {
    val filename = args[0]

    val sequences = File(filename).readLines()
        .map { line ->
            line.split(' ').map { it.toInt() }
        }

    val result1 = sequences.map(::findNext).sum()
    val result2 = sequences.map { it.reversed() }.map(::findNext).sum()

    println(result1)
    println(result2)
}
