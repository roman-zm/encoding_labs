package tree

data class TreeNode<T: Comparable<T>>(
    override var parent: ITreeNode<T>?,
    override var firstChild: ITreeNode<T>?,
    override var secondChild: ITreeNode<T>?,
    override val value: T?
) : ITreeNode<T> {
    override fun setFirst(node: ITreeNode<T>) {
        node.parent = this
        firstChild = node
    }

    override fun setSecond(node: ITreeNode<T>) {
        node.parent = this
        secondChild = node
    }

    override fun toString() =
        "TreeNode(parent=" +
                "${parent?.javaClass?.simpleName}," +
                "firstChild=${firstChild?.javaClass?.simpleName}," +
                "secondChild=${firstChild?.javaClass?.simpleName}," +
                "value=$value"
}

/**
 * Factory function that automatically sets parent for child's
 */
fun <T: Comparable<T>> node(
    value: T? = null,
    parent: ITreeNode<T>? = null,
    firstChild: ITreeNode<T>? = null,
    secondChild: ITreeNode<T>? = null
): ITreeNode<T> = TreeNode(parent, firstChild, secondChild, value).apply {
    firstChild?.parent = this
    secondChild?.parent = this
}
