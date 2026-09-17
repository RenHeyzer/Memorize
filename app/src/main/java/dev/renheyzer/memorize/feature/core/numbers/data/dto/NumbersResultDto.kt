package dev.renheyzer.memorize.feature.core.numbers.data.dto

import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import kotlinx.serialization.Serializable

@Serializable
data class NumbersResultDto(
    val id: String = "",
    val params: NumbersParamDto = NumbersParamDto(),
    val rounds: List<RoundResultDto> = emptyList(),
    val startedAtMillis: Long = 0L,
    val recallStartedAtMillis: Long = 0L,
    val completedAtMillis: Long = 0L,
    val totalSpentMillis: Long = 0L
)

fun NumbersResultDto.toDomain(): NumbersResult {
    return NumbersResult(
        id = id,
        params = params.toDomain(),
        rounds = rounds.map { it.toDomain() },
        startedAtMillis = startedAtMillis,
        completedAtMillis = completedAtMillis,
    )
}

fun NumbersResult.toDto(): NumbersResultDto {
    return NumbersResultDto(
        id = id,
        params = params.toDto(),
        rounds = rounds.map { it.toDto() },
        startedAtMillis = startedAtMillis,
        completedAtMillis = completedAtMillis,
        totalSpentMillis = totalSpentMillis
    )
}