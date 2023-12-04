import java.io.File

fun getNumberSet(text: String) : Set<Int> {
    return text.split("\\s+".toRegex()).map { it.toInt() }.toSet()
}

fun getMatchingNumbers(text: String) : Int {
    val s = text.split(':', '|').map { it.trim() }
    val winningNumbers = getNumberSet(s[1])
    val myNumbers = getNumberSet(s[2])
    return winningNumbers.intersect(myNumbers).size
}

fun getCardValue(matches: Int) : Int {
    if (matches == 0) {
        return 0
    }
    return 1.shl(matches - 1)
}

fun main(args: Array<String>) {
    val filename = args[0]

    val cards = File(filename).readLines().map(::getMatchingNumbers)

    val result1 = cards.map(::getCardValue).sum()

    val numberOfCards = MutableList(cards.size) { 1 }
    cards.forEachIndexed { i, matches ->
        for (j in i + 1 .. i + matches) {
            numberOfCards[j] += numberOfCards[i]
        }
    }

    val result2 = numberOfCards.sum()

    println(result1)
    println(result2)
}