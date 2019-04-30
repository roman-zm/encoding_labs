package charrate

data class CharRate(
    override val character: Char?,
    override val rate: Int
) : ICharRate {
    override fun increase() = copy(rate = rate + 1)
    override fun toString() = "$character: $rate"
}

fun charRate(character: Char? = null, rate: Int = 1): ICharRate = CharRate(character, rate)

data class ComplexCharRate(
    val charRateList: List<ICharRate>
) : ICharRate {
    override val rate: Int = charRateList.map { it.rate }.sum()
    override val character: Char? = null
    override fun increase(): ICharRate = this
}

fun complexCharRate(charList: List<ICharRate>): ICharRate = ComplexCharRate(charList)
