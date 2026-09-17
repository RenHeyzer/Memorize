package dev.renheyzer.memorize.feature.core.numbers.domain.model

import dev.renheyzer.memorize.core.models.GameRandomConfig
import kotlinx.serialization.Serializable

@Serializable
data class RoundResult(
    val id: String = "",
    val details: List<AnswerResult>,
    val randomConfig: GameRandomConfig,
    val startedAtMillis: Long,
    val recallStartedAtMillis: Long,
    val completedAtMillis: Long,
) {
    init {
        require(recallStartedAtMillis >= startedAtMillis) {
            "Recall start time must be greater than or equal to start time"
        }
        require(completedAtMillis >= recallStartedAtMillis) {
            "Completed time must be greater than or equal to recall start time"
        }
    }

    val totalCount: Int = details.size

    val correctCount: Int = details.count { it.isCorrect }

    val accuracy: Float =
        if (totalCount == 0) 0f else correctCount.toFloat() / totalCount

    val memorizationTimeMillis: Long
        get() = recallStartedAtMillis - startedAtMillis

    val recallTimeMillis: Long
        get() = completedAtMillis - recallStartedAtMillis

    val totalSpentMillis: Long
        get() = completedAtMillis - startedAtMillis

    val isCleared: Boolean = accuracy == 1f
}
