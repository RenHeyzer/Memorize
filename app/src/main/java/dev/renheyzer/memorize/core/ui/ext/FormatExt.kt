package dev.renheyzer.memorize.core.ui.ext

import java.text.NumberFormat

fun Float.toPercentString(): String {
    val formatter = NumberFormat.getPercentInstance().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return formatter.format(this)
}