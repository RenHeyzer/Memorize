package dev.renheyzer.memorize.feature.core.numbers.presentation.utils

// Split it into columns × rows parts.
fun <T> List<T>.selectPart(partsCount: Int, targetIndex: Int): List<T> {
    if (isEmpty() || partsCount <= 0) return emptyList()
    val chunkSize = (size + partsCount - 1) / partsCount
    return chunked(chunkSize).getOrNull(targetIndex) ?: emptyList()
}