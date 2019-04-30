package encoder

interface Encoder {
    fun encode(source: String): Pair<String, Map<Char, String>>

    fun createCharRateMap(toEncode: String) =
        mutableMapOf<Char?, Int>().apply {
            toEncode.forEach { merge(it, 1, Integer::sum) }
        }.toMap()

}