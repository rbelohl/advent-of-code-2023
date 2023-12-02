import java.io.File

val digitsNumbers = mapOf(
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9)

val digitsWords = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun getValue(line: String, includeWords: Boolean) : Int {
    var first = 0
    var firstIndex = line.length
    var last = 0
    var lastIndex = -1

    val digits = when (includeWords) {
        true -> digitsNumbers + digitsWords
        false -> digitsNumbers
    }

    digits.forEach { (digit, value) ->
        val index1 = line.indexOf(digit)
        if (index1 >= 0 && index1 < firstIndex) {
            first = value
            firstIndex = index1
        }
        val index2 = line.lastIndexOf(digit)
        if (index2 > lastIndex) {
            last = value
            lastIndex = index2
        }
    }
    return first * 10 + last
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val result1 = lines.sumOf { getValue(it, false) }
    val result2 = lines.sumOf { getValue(it, true) }
    println(result1)
    println(result2)
}