package dev.renheyzer.memorize.feature.core.cards.data.dto

import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import kotlinx.serialization.Serializable

@Serializable
data class CardsResultDto(
    val id: String = "",
    val params: CardsParamDto = CardsParamDto(),
    val details: List<CardsAnswerResultDto> = emptyList(),
    val startedAtMillis: Long = 0L,
    val recallStartedAtMillis: Long = 0L,
    val completedAtMillis: Long = 0L,
)

fun CardsResultDto.toDomain(): CardsResult {
    return CardsResult(
        id = id,
        params = params.toDomain(),
        details = details.map { it.toDomain() },
        startedAtMillis = startedAtMillis,
        recallStartedAtMillis = recallStartedAtMillis,
        completedAtMillis = completedAtMillis,
    )
}

fun CardsResult.toDto(): CardsResultDto {
    return CardsResultDto(
        id = id,
        params = params.toDto(),
        details = details.map { it.toDto() },
        startedAtMillis = startedAtMillis,
        recallStartedAtMillis = recallStartedAtMillis,
        completedAtMillis = completedAtMillis,
    )
}