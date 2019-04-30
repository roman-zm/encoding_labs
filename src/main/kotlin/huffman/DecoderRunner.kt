package huffman

import java.io.File

fun main() {
    val decoder = HuffmanDecoder()

    val encodedString = File("output.txt").readText()

    val lines = File("chars.txt").readLines()

    val charCodesMap = mutableMapOf<Char, String>().apply {
        lines.forEach {
            put(it[0], it.substringAfter(": "))
        }
    }

    val result = decoder.decode(encodedString, charCodesMap)
    println(result)


}