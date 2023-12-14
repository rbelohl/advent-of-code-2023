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

fun List<String>.transposeStrings() : List<String> {
    return this.map { it.toCharArray().toList() }
        .transpose()
        .map { it.joinToString("") }
}

fun slide(input: List<String>, reverse: Boolean) : List<String> {
    val comparator = when (reverse) {
        true -> reverseOrder<Char>()
        false -> naturalOrder<Char>()
    }

    return input.map { col ->
        col.split('#').map {
            it.toCharArray().sortedWith(comparator).joinToString("")
        }.joinToString("#")
    }
}

fun cycle(input: List<String>) : List<String> {
    // North
    var tilted = slide(input, true)

    // West
    tilted = tilted.transposeStrings()
    tilted = slide(tilted, true)

    // South
    tilted = tilted.transposeStrings()
    tilted = slide(tilted, false)

    // East
    tilted = tilted.transposeStrings()
    tilted = slide(tilted, false)

    return tilted.transposeStrings()
}

fun calculateLoad(input: List<String>) : Int {
    return input.map { col ->
        col.reversed().mapIndexed { index, c ->
            if (c == 'O') index + 1 else 0
        }.sum()
    }.sum()
}

fun main(args: Array<String>) {
    val filename = args[0]

    val input = File(filename).readLines().transposeStrings()

    val tilted = slide(input, true)

    val result1 = calculateLoad(tilted)

    val seen = mutableMapOf<List<String>, Int>()
    val loads = mutableListOf<Int>()
    val maxCycles = 1000000000
    var cycled = input
    var result2 = 0
    for (i in 0..maxCycles) {
        loads.add(calculateLoad(cycled))
        if (seen.containsKey(cycled)) {
            val lastIndex = seen[cycled]!!
            val cycleLength = i - lastIndex
            result2 = loads[lastIndex + (maxCycles - i) % cycleLength]
            break
        }
        seen[cycled] = i
        cycled = cycle(cycled)
    }

    println(result1)
    println(result2)
}