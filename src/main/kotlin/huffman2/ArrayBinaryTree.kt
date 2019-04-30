package huffman2

import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import java.lang.Math.pow
import kotlin.math.log2

class ArrayBinaryTree {

}

fun main() {
    val num = 29.0
    val i = Math.log(num)
    val n = Math.pow(2.0, i+1)
    println(n)

    val f = { n: Number ->
        val d = n.toDouble()
        val i = Math.log(d)
        Math.pow(2.0, (i + 0.5).toInt().toDouble())
    }


    val x = IntArray(168) { it + 1 }
    val y = x.map { log2(it.toDouble()).toInt() }
        .map { pow(2.0, it.toDouble()) - pow(2.0, (it - 1).toDouble()) }
        .map { it.toInt() }
        .toIntArray()

    val chart = QuickChart.getChart("Sample", "X", "Y", "y(x)",
        x.map { it.toDouble() }, y.map { it.toDouble() })

    SwingWrapper(chart).displayChart()

}
