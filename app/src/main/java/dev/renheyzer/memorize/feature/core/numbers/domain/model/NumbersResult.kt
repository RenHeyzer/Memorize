package dev.renheyzer.memorize.feature.core.numbers.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NumbersResult(
    val id: String = "",
    val params: NumbersParam,
    val rounds: List<RoundResult>,
    val startedAtMillis: Long,
    val completedAtMillis: Long,
) {
    init {
        require(rounds.sumOf { it.totalCount } == params.quantity) {
            "Total numbers count must be equal to quantity"
        }
        require(rounds.isNotEmpty()) {
            "At least one round is required"
        }
        require(completedAtMillis >= startedAtMillis) {
            "Completed time must be greater than or equal to start time"
        }
    }

    val totalCount: Int = rounds.sumOf { it.totalCount }

    val correctCount: Int = rounds.sumOf { it.correctCount }

    val totalAccuracy: Float = rounds.sumOf { it.accuracy.toDouble() }.toFloat() / rounds.size

    val totalSpentMillis: Long
        get() = completedAtMillis - startedAtMillis
}