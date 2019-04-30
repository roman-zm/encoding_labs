package tree

interface ITreeNode<T: Comparable<T>>: Comparable<ITreeNode<T>> {
    var parent: ITreeNode<T>?

    var firstChild: ITreeNode<T>?
    var secondChild: ITreeNode<T>?

    val value: T?

    override fun compareTo(other: ITreeNode<T>) = compareValues(value, other.value)
    fun setFirst(node: ITreeNode<T>)
    fun setSecond(node: ITreeNode<T>)
}