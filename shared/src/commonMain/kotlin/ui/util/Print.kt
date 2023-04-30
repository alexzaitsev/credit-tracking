package ui.util

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDateTime
import kotlin.math.abs

fun LocalDateTime.print(twoLines: Boolean = false) =
    "$year-${monthNumber.applyZero()}-${dayOfMonth.applyZero()}" +
            "${if (twoLines) '\n' else ' '}" +
            "${hour.applyZero()}:${minute.applyZero()}"

private fun Int.applyZero() = if (this < 10) "0$this" else "$this"

val Float.zeroBasedColor: Color
    get() = if (this < 0) Color.Red else Color.Green

fun Float.printAmount() = if (this < 0) "-$${abs(this)}" else "$$this"
