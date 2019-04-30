package shennon

import charrate.*
import encoder.Encoder
import tree.ITreeNode
import tree.node

class ShannonEncoder: Encoder {
    override fun encode(source: String): Pair<String, Map<Char, String>> {
        val charRateMap = createCharRateMap(source)
        val tree = createBinaryTree(charRateMap)

        val charCodeMap = generateCharCodeMap(tree)

        return createEncodedString(source, charCodeMap) to charCodeMap
    }

    private fun createEncodedString(source: String, charCodeMap: Map<Char, String>) =
        StringBuilder().apply {
            source.forEach { append(charCodeMap[it]) }
        }.toString()

    private fun createBinaryTree(charRateMap: Map<Char?, Int>): ITreeNode<ICharRate> {
        val charList = charRateMap.map { charRate(it.key, it.value) }
        val charRate = complexCharRate(charList)
        val treeNode = node(charRate)

        createBinaryTree(treeNode)

        return treeNode
    }

    private fun createBinaryTree(treeNode: ITreeNode<ICharRate>) {
        val charRate = treeNode.value
        if (charRate is ComplexCharRate) {
            val charList = listOf(*charRate.charRateList.toTypedArray()).sortedDescending()

            var leftRate = 0
            var rightRate = 0
            val leftList = mutableListOf<ICharRate>()
            val rightList = mutableListOf<ICharRate>()

            charList.forEach {
                if (leftRate > rightRate) {
                    rightRate += it.rate
                    rightList += it
                } else {
                    leftRate += it.rate
                    leftList += it
                }
            }

            val leftCharRate = when {
                leftList.size > 1 -> ComplexCharRate(leftList)
                leftList.isNotEmpty() -> charRate(leftList.first().character, leftList.first().rate)
                else -> null
            }?.let { node(it) }

            val rightCharRate = when {
                rightList.size > 1 -> ComplexCharRate(rightList)
                rightList.isNotEmpty() -> charRate(rightList.first().character, rightList.first().rate)
                else -> null
            }?.let { node(it) }

            leftCharRate?.let {
                createBinaryTree(it)
                treeNode.setFirst(it)
            }

            rightCharRate?.let {
                createBinaryTree(it)
                treeNode.setSecond(it)
            }
        }
    }

    private fun generateCharCodeMap(tree: ITreeNode<ICharRate>) =
        mutableMapOf<Char, String>().apply {
            generateCharCodeMap(tree, "", this)
        }.toMap()

    private fun generateCharCodeMap(
        tree: ITreeNode<ICharRate>,
        code: String,
        codeMap: MutableMap<Char, String>
    ) {
        tree.firstChild?.let { generateCharCodeMap(it, code + "0", codeMap) }
        tree.secondChild?.let { generateCharCodeMap(it, code + "1", codeMap) }

        //only leafs should have a char
        tree.value?.character?.let { codeMap += it to code }
    }

}