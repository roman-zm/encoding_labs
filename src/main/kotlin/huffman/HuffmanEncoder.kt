package huffman

import charrate.ICharRate
import charrate.charRate
import com.github.penemue.keap.PriorityQueue
import encoder.Encoder
import tree.ITreeNode
import tree.node

class HuffmanEncoder: Encoder {
    override fun encode(source: String): Pair<String, Map<Char, String>> {
        val charRateMap = createCharRateMap(source)
        val nodeQueue = createNodeQueue(charRateMap)

        val tree = generateBinaryTree(nodeQueue.copy())
        val charCodeMap = generateCharCodeMap(tree)

        return createEncodedString(source, charCodeMap) to charCodeMap
    }

    private fun createEncodedString(source: String, charCodeMap: Map<Char, String>) =
        StringBuilder().apply {
            source.forEach { append(charCodeMap[it]) }
        }.toString()

    /**
     * Facade function for recursive map generator
     */
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

    private fun generateBinaryTree(nodes: PriorityQueue<ITreeNode<ICharRate>>): ITreeNode<ICharRate> {

        //PriorityQueue implemented as a min heap
        while (nodes.size > 1) {
            val firstChild = nodes.poll()
            val secondChild = nodes.poll()

            val value = charRate(rate = (firstChild?.value?.rate ?: 0) + (secondChild?.value?.rate ?: 0))

            nodes.add(
                node(
                    value = value,
                    firstChild = firstChild,
                    secondChild = secondChild
                )
            )
        }

        return nodes.poll() ?: node()
    }

    override fun createCharRateMap(toEncode: String) =
        mutableMapOf<Char?, Int>().apply {
            toEncode.forEach { merge(it, 1, Integer::sum) }
        }.toMap()

    private fun createNodeQueue(charRateMap: Map<Char?, Int>) =
        PriorityQueue(charRateMap.map { entry -> node(value = charRate(entry.key, entry.value)) } )

}

private fun <E> PriorityQueue<E>.copy() = PriorityQueue(this)
