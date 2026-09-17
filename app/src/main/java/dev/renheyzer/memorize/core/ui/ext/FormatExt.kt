package dev.renheyzer.memorize.core.ui.ext

import java.text.NumberFormat

fun Float.toPercentString(): String {
    val formatter = NumberFormat.getPercentInstance().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return formatter.format(this)
}

fun Long.formatMillisAsMMSS(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return "%02d:%02d".format(minutes, seconds)
}