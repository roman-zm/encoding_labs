package charrate

interface ICharRate: Comparable<ICharRate> {
    val character: Char?
    val rate: Int

    override fun compareTo(other: ICharRate) = compareValues(rate, other.rate)

    fun increase(): ICharRate
}