import java.io.File

fun couldSatisfy(springs: String, groups: List<Int>) : Boolean {
    var runningCount = 0
    var groupIndex = 0

    for (c in springs) {
        if (c == '?') {
            break
        }
        if (c == '#') {
            if (groupIndex >= groups.size) {
                return false
            }
            runningCount++
            if (runningCount > groups[groupIndex]) {
                return false
            }
        }
        if (c == '.') {
            if (runningCount > 0 && runningCount != groups[groupIndex]) {
                return false
            }
            if (runningCount > 0) {
                runningCount = 0
                groupIndex++
            }
        }
    }
    return true
}

fun satisfies(springs: String, groups: List<Int>) : Boolean {
    val splitSprings =  springs.split('.')
        .filter { it.isNotBlank() }

    if (splitSprings.size != groups.size) {
        return false
    }
    return splitSprings
        .zip(groups)
        .all { (spring, group) -> spring.length == group }
}

fun solve(springs: String, groups: List<Int>) : Long {
    if (!couldSatisfy(springs, groups)) {
        return 0
    }
    if (!springs.contains('?')) {
        if (satisfies(springs, groups)) {
            return 1
        }
        return 0
    }
    val replaceOperational = springs.replaceFirst('?', '#')
    val replaceDamaged = springs.replaceFirst('?', '.')

    return solve(replaceOperational, groups) + solve(replaceDamaged, groups)
}

fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines()
        .map { it.split(' ') }
        .map { split ->
            Pair(split[0], split[1].split(',').map { it.toInt() })
        }

    val result1 = input.map { solve(it.first, it.second) }.sum()

    println(result1)
}