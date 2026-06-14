package dev.renheyzer.memorize.feature.core.cards.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CardsResult(
    val id: Long = 0L,
    val params: CardsParam,
    val details: List<AnswerResult>,
    val startedAtMillis: Long,
    val recallStartedAtMillis: Long,
    val completedAtMillis: Long,
) {
    init {
        require(details.size == params.quantity) {
            "Result details size must be equal to params quantity"
        }
        require(recallStartedAtMillis >= startedAtMillis) {
            "Recall start time must be greater than or equal to start time"
        }
        require(completedAtMillis >= recallStartedAtMillis) {
            "Completed time must be greater than or equal to recall start time"
        }
    }

    val totalCount = details.size
    val correctCount = details.count { it.isCorrect }

    val accuracy: Float = if (totalCount == 0) 0f else correctCount.toFloat() / totalCount

    val memorizationSpentMillis: Long
        get() = recallStartedAtMillis - startedAtMillis

    val recallSpentMillis: Long
        get() = completedAtMillis - recallStartedAtMillis

    val totalSpentMillis: Long
        get() = completedAtMillis - startedAtMillis
}