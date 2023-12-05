import java.io.File

fun findMapping(number: Long, map: MutableList<List<Long>>) : Long {
    map.forEach {
        val destination = it[0]
        val source = it[1]
        val length = it[2]

        if (number in source ..< source + length) {
            return number + destination - source
        }
    }
    return number
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()
    val seeds = lines[0].split(": ")[1]
        .split(" ")
        .map { it.toLong() }

    val maps = MutableList(1) { mutableListOf<List<Long>>() }
    var mapIndex = 0

    lines.drop(3).forEach { line ->
        if (line.isBlank()) {
            return@forEach
        }
        if (line.contains("map")) {
            maps.add(mutableListOf())
            mapIndex++
            return@forEach
        }
        maps[mapIndex].add(line.split(" ").map { it.toLong() })
    }

    val seedsResult = mutableListOf<Long>()
    seeds.forEach { seed ->
        var current = seed
        maps.forEach { map ->
            current = findMapping(current, map)
        }
        seedsResult.add(current)
    }
    val result1 = seedsResult.min()

    println(result1)
}