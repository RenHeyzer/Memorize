package dev.renheyzer.memorize.feature.core.numbers.domain.model

data class GameResult(
    val id: Long = 0L,
    val details: List<AnswerResult>,
    val correctCount: Int,
    val totalCount: Int,
    val scorePercentage: Float
)
