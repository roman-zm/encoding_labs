package hamming

fun main() {
    val source = "0000101101110101011100011"
    val copyToCheck = StringBuilder(source)

    val controlBits = controlBitIndexlist(source)

    val createBitMask = { bit: Int, size: Int ->
        val maskUnit = StringBuilder().apply {
            repeat(bit + 1) { append('1') }
            repeat(bit + 1) { append('0') }
        }.toString()

        StringBuilder().apply {
            repeat(bit) { append('0') }
            while (length < size) {
                append(maskUnit)
                if (length > size) delete(size, length)
            }
        }.toString()

    }

    val controlBitMask = controlBits.map { createBitMask(it, source.length) }
    controlBits.forEach { copyToCheck[it] = '0' }
    controlBits.forEachIndexed { i, bitIndex ->
        val mask = controlBitMask[i]
        val bitValue = mask
            .filterIndexed { index, c -> c == '1' && copyToCheck[index] == '1' }
            .count() % 2

        copyToCheck[bitIndex] = when (bitValue) {
            1 -> '1'
            else -> '0'
        }
    }

    val errorIndex = controlBits.mapNotNull {
        if (source[it] != copyToCheck[it]) it else null
    }
//    val errorIndex = source.mapIndexedNotNull { index, c ->
//        if (c != copyToCheck[index]) index else null
//    }

    if (errorIndex.isNotEmpty()) {
        val index = errorIndex.sum() + 1
        println("Ошибка в бите $index")
        val char = copyToCheck[index]
        copyToCheck[index] = when (char) {
            '0' -> '1'
            else -> '0'
        }
    }

    controlBits.asReversed().forEach { copyToCheck.deleteCharAt(it) }

    println(copyToCheck.toString())

}

private fun controlBitIndexlist(source: String): List<Int> {
        val getBitIndex = { index: Int -> Math.pow(2.0, index.toDouble()).toInt() - 1 }
        val mutableBitList = mutableListOf<Int>()

        var i = 0
        while (true) {
            val bitIndex = getBitIndex(i)
            i++
            if (bitIndex < source.length - 1) {
                mutableBitList += bitIndex
            } else {
                return mutableBitList
            }
        }
    }
