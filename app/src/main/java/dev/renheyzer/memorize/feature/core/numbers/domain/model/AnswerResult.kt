package dev.renheyzer.memorize.feature.core.numbers.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AnswerResult(
    val index: Int,
    val number: Int,
    val answer: Int? = null,
) {
    val isCorrect: Boolean = number == answer
}