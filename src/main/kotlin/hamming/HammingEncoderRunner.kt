package hamming


fun main() {
    val input = "01010111110011100011"

    val encoder = HammingEncoder()

    encoder.encode(input)
}