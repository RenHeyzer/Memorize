package dev.renheyzer.memorize.core.ui.timer

fun Long.toDisplaySeconds(): Long {
    if (this <= 0L) return 0L
    return (this + 999L) / 1000L
}

fun Long.formatAsTimerMMSS(): String {
    val minutes = this / 60
    val seconds = this % 60

    return buildString(capacity = 5) {
        appendTwoDigits(minutes)
        append(':')
        appendTwoDigits(seconds)
    }
}

fun Long.formatAsTimerHHMMSS(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return buildString(capacity = 8) {
        appendTwoDigits(hours)
        append(':')
        appendTwoDigits(minutes)
        append(':')
        appendTwoDigits(seconds)
    }
}


private fun StringBuilder.appendTwoDigits(value: Long) {
    if (value < 10) append('0')
    append(value)
}