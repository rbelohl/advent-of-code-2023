import java.io.File

fun hash(string: String) : Int {
    return string
        .map { c -> c.code }
        .fold(0) { acc, i -> ((acc + i) * 17) % 256 }
}

fun splitLabel(string: String) : Pair<String, String> {
    return Pair(string.takeWhile { it.isLetter() }, string.dropWhile { it.isLetter() })
}

fun main(args: Array<String>) {
    val filename = args[0]
    val init = File(filename).readLines().first().split(',')

    val result1 = init.sumOf(::hash)

    val boxes = List<MutableList<Pair<String, Int>>>(256) { mutableListOf() }

    init.map(::splitLabel).forEach { (label, instruction) ->
        val box = boxes[hash(label)]

        if (instruction.first() == '-') {
            box.removeIf { it.first == label }
        } else {
            val focalLength = instruction.last().digitToInt()
            val newEntry = Pair(label, focalLength)
            val index = box.indexOfFirst { it.first == label }
            if (index < 0) {
                box.add(newEntry)
            } else {
                box[index] = newEntry
            }
        }
    }

    val result2 = boxes
        .mapIndexed { boxNumber, box ->
            box.mapIndexed { lensNumber, lens ->
                (boxNumber + 1) * (lensNumber + 1) * lens.second
            }.sum()
        }.sum()

    println(result1)
    println(result2)
}
