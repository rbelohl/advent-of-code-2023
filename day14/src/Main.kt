import java.io.File

fun <T> List<List<T>>.transpose() : List<List<T>> {
    val width = this[0].size;
    val height = this.size;
    return List(height) { i ->
        List(width) { j ->
            this[j][i]
        }
    }
}

fun main(args: Array<String>) {
    val filename = args[0]

    val input = File(filename).readLines()
        .map { it.toCharArray().toList() }
        .transpose()
        .map { it.joinToString("") }

    val tilted = input.map { col ->
        col.split('#').map {
            it.toCharArray().sortedDescending().joinToString("")
        }.joinToString("#")
    }

    val result1 = tilted.map { col ->
        col.reversed().mapIndexed { index, c ->
            if (c == 'O') index + 1 else 0
        }.sum()
    }.sum()

    println(result1)
}