import java.io.File

fun findMapping(number: Long, map: MutableList<List<Long>>, reverse : Boolean) : Long {
    map.forEach {
        val destination = if (reverse) it[1] else it[0]
        val source = if (reverse) it[0] else it[1]
        val length = it[2]

        if (number in source ..< source + length) {
            return number + destination - source
        }
    }
    return number
}

fun part1(seeds: List<Long>, maps: MutableList<MutableList<List<Long>>>) : Long {
    val seedsResult = mutableListOf<Long>()
    seeds.forEach { seed ->
        var current = seed
        maps.forEach { map ->
            current = findMapping(current, map, false)
        }
        seedsResult.add(current)
    }
    return seedsResult.min()
}

fun part2(seeds: List<Long>, maps: MutableList<MutableList<List<Long>>>) : Long {
    val seedRanges = seeds.chunked(2)
    var i = 0.toLong()
    while (true) {
        var current = i
        maps.reversed().forEach { map ->
            current = findMapping(current, map, true)
        }
        seedRanges.forEach {
            if (current in it[0] ..< it[0] + it[1]) {
                return i
            }
        }
        i++
    }
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

    val result1 = part1(seeds, maps)
    println(result1)

    val result2 = part2(seeds, maps)
    println(result2)
}