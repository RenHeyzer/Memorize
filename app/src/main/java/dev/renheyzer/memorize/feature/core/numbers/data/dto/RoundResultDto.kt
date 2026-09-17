package dev.renheyzer.memorize.feature.core.numbers.data.dto

import dev.renheyzer.memorize.core.models.GameRandomConfig
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import kotlinx.serialization.Serializable

@Serializable
data class RoundResultDto(
    val id: String = "",
    val details: List<AnswerResultDto> = emptyList(),
    val randomConfig: GameRandomConfig = GameRandomConfig(seed = 0L, algorithmVersion = 1),
    val startedAtMillis: Long = 0L,
    val recallStartedAtMillis: Long = 0L,
    val completedAtMillis: Long = 0L,
    val isCleared: Boolean = false
)

fun RoundResultDto.toDomain(): RoundResult {
    return RoundResult(
        id = id,
        details = details.map { it.toDomain() },
        randomConfig = randomConfig,
        startedAtMillis = startedAtMillis,
        recallStartedAtMillis = recallStartedAtMillis,
        completedAtMillis = completedAtMillis,
    )
}

fun RoundResult.toDto(): RoundResultDto {
    return RoundResultDto(
        id = id,
        details = details.map { it.toDto() },
        randomConfig = randomConfig,
        startedAtMillis = startedAtMillis,
        recallStartedAtMillis = recallStartedAtMillis,
        completedAtMillis = completedAtMillis,
        isCleared = isCleared
    )
}