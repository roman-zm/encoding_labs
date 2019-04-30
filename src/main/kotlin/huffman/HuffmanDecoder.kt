package huffman
class HuffmanDecoder {
    fun decode(encodedString: String, charCodesMap: Map<Char, String>): String {

        val map = transformMap(charCodesMap)

        var result = ""
        var prefix = ""
        encodedString.forEach {
            prefix += it
            map[prefix]?.let { char ->
                result += char
                prefix = ""
            }
        }
        return result
    }

    private fun transformMap(charCodesMap: Map<Char, String>) =
        mutableMapOf<String, String>().apply {
            charCodesMap.forEach { c: Char, s: String -> put(s, c.toString()) }
        }.toMap()
    }
