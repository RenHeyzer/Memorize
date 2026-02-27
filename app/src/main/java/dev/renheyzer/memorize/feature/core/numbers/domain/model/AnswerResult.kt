package dev.renheyzer.memorize.feature.core.numbers.domain.model

data class AnswerResult(
    val id: Int,
    val number: Int,
    val answer: Int? = null,
    val isCorrect: Boolean = false,
)