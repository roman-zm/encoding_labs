package hamming

class HammingEncoder {
    fun encode(source: String): String {
        val encodedBuilder = StringBuilder(source)

        val bits = insertControlBits(encodedBuilder)

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

        val controlBitMask = bits.map { createBitMask(it, encodedBuilder.length) }

        bits.forEachIndexed { i, bitIndex ->
            val mask = controlBitMask[i]
            val bitValue = mask
                .filterIndexed { index, c -> c == '1' && encodedBuilder[index] == '1' }
                .count() % 2

            encodedBuilder[bitIndex] = when (bitValue) {
                1 -> '1'
                else -> '0'
            }
        }

        println(encodedBuilder.toString())
        return encodedBuilder.toString()
    }

    private fun insertControlBits(encodedBuilder: StringBuilder): List<Int> {
        val getBitIndex = { index: Int -> Math.pow(2.0, index.toDouble()).toInt() - 1 }
        val mutableBitList = mutableListOf<Int>()

        var i = 0
        while (true) {
            val bitIndex = getBitIndex(i)
            i++
            if (bitIndex < encodedBuilder.length - 1) {
                encodedBuilder.insert(bitIndex, '0')
                mutableBitList += bitIndex
            } else {
                return mutableBitList
            }
        }
    }

}
