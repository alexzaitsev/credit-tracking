package ui.util

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDateTime
import kotlin.math.abs
import kotlin.math.roundToInt

fun LocalDateTime.print(twoLines: Boolean = false) =
    "$year-${monthNumber.applyZero()}-${dayOfMonth.applyZero()}" +
            "${if (twoLines) '\n' else ' '}" +
            "${hour.applyZero()}:${minute.applyZero()}"

private fun Int.applyZero() = if (this < 10) "0$this" else "$this"

val Double.zeroBasedColor: Color
    get() = if (this < 0) Color.Red else Color.Green

fun Double.printAmount() = if (this < 0) {
    "-$${abs(this).round()}"
} else {
    "${if (this.round() == 0.0) ' ' else '+'}$${this.round()}".trim()
}

private fun Double.round(): Double = (this * 100.0).roundToInt() / 100.0
