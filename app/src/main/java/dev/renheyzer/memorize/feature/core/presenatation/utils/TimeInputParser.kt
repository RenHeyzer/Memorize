package dev.renheyzer.memorize.feature.core.presenatation.utils

fun parseTimeDigitsToSeconds(input: String): Int? {
    val digits = input.filter(Char::isDigit)

    if (digits.isBlank()) return null
    if (digits.length > 4) return null

    return when (digits.length) {
        1, 2 -> {
            digits.toIntOrNull()
                ?.takeIf { seconds -> seconds > 0 }
        }

        3, 4 -> {
            val minutesPart = digits.dropLast(2)
            val secondsPart = digits.takeLast(2)

            val minutes = minutesPart.toIntOrNull() ?: return null
            val seconds = secondsPart.toIntOrNull() ?: return null

            if (seconds !in 0..59) return null

            val totalSeconds = minutes * 60 + seconds
            totalSeconds.takeIf { it > 0 }
        }

        else -> null
    }
}