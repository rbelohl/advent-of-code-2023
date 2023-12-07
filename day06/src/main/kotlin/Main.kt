import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val filename = args[0]
    val lines = File(filename).readLines()
    val times = lines[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }
    val distances = lines[1].split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }

    val results = mutableListOf<Long>()
    times.zip(distances).forEach { (time, distance) ->
        val a = -1
        val b = time.toDouble()
        // val b = 63789468.toDouble()
        val c = -distance.toDouble()
        // val c = (-411127420471035).toDouble()

        val D = b * b - 4 * a * c
        val lol = 1e-6
        val x1 = ((-b - sqrt(D)) / (2 * a)) - lol
        val x2 = ((-b + sqrt(D)) / (2 * a)) + lol

        val result = floor(x1).toLong() - ceil(x2).toLong() + 1
        results.add(result)
        println(result)
    }
    println(results.reduce{ a, b -> a * b})

    // hold * (time - hold) > distance
    // hold * time - hold^2 > distance
    // -hold^2 + time * hold - distance > 0
}