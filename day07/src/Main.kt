import java.io.File

fun compareHandsPart1(hand1: String, hand2: String) : Int {
    return compareHands(hand1, hand2, 1)
}

fun compareHandsPart2(hand1: String, hand2: String) : Int {
    return compareHands(hand1, hand2, 2)
}
fun compareHands(hand1: String, hand2: String, part: Int) : Int {
    val (cardOrder, type1, type2) = when(part) {
        1 -> Triple("AKQJT98765432", getHandType(hand1), getHandType(hand2))
        2 -> Triple("AKQT98765432J", getHandType(convertJoker(hand1)), getHandType(convertJoker(hand2)))
        else -> { throw Exception("invalid part") }
    }

    if (type1 < type2) {
        return -1
    }
    if (type1 > type2) {
        return 1
    }

    hand1.zip(hand2).forEach { (card1, card2) ->
        val index1 = cardOrder.indexOf(card1)
        val index2 = cardOrder.indexOf(card2)
        if (index1 < index2) {
            return 1
        }
        if (index1 > index2) {
            return -1
        }
    }

    return 0
}

fun getHandType(hand: String) : Int {
    val countsSorted = hand.associate { c ->
        c to hand.count { it == c }
    }.values.sortedDescending()

    if (countsSorted[0] == 5) {
        return 6 // five of a kind
    }
    if (countsSorted[0] == 4) {
        return 5 // four of a kind
    }
    if (countsSorted[0] == 3) {
        if (countsSorted[1] == 2) {
            return 4 // full house
        }
        return 3 // three of a kind
    }
    if (countsSorted[0] == 2) {
        if (countsSorted[1] == 2) {
            return 2 // two pair
        }
        return 1 // one pair
    }

    return 0 // high card
}

fun convertJoker(hand: String) : String {
    if (hand == "JJJJJ") {
        return hand
    }
    val highestCount = hand.associate { c ->
        c to hand.count { it == c }
    }.entries.filter{ it.key != 'J'}.maxBy { it.value }.key

    return hand.replace('J', highestCount)
}

fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines()
        .map { it.split(" ") }
        .map { Pair(it[0], it[1]) }


    val result1 = input
        .sortedWith(compareBy(Comparator(::compareHandsPart1)) { it.first })
        .map { it.second.toInt() }
        .mapIndexed { i, bid ->
            (i + 1) * bid
        }
        .sum()

    val result2 = input
        .sortedWith(compareBy(Comparator(::compareHandsPart2)) { it.first })
        .map { it.second.toInt() }
        .mapIndexed { i, bid ->
            (i + 1) * bid
        }
        .sum()

    println(result1)
    println(result2)
}