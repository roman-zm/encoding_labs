package huffman

import java.io.File

fun main() {
    val encoder = HuffmanEncoder()

    val input = File("input.txt").readText()

    val (output, codesMap) = encoder.encode(input)

    println(output)
    codesMap.forEach { println("${it.key}: ${it.value}") }

    File("output.txt").printWriter().use {
        it.println(output)
    }

    File("chars.txt").printWriter().use { writer ->
        codesMap.forEach { writer.println("${it.key}: ${it.value}") }
    }

    val charRate = encoder.createCharRateMap(input)
    val length = input.length.toFloat()

    charRate.forEach {c: Char?, i: Int ->
        println("$c: ${i/length}")
    }

}
