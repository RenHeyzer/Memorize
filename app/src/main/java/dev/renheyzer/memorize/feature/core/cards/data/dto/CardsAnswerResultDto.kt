package dev.renheyzer.memorize.feature.core.cards.data.dto

import dev.renheyzer.memorize.feature.core.cards.domain.model.AnswerResult
import kotlinx.serialization.Serializable

@Serializable
data class CardsAnswerResultDto(
    val id: Int = 0,
    val memorizedCard: CardDto? = null,
    val recalledCard: CardDto? = null,
    val correct: Boolean = false,
)

fun CardsAnswerResultDto.toDomain(): AnswerResult {
    return AnswerResult(
        id = id,
        memorizedCard = memorizedCard?.toDomain() ?: throw IllegalArgumentException("Memorized Card cannot be null"),
        recalledCard = recalledCard?.toDomain()
    )
}

fun AnswerResult.toDto(): CardsAnswerResultDto {
    return CardsAnswerResultDto(
        id = id,
        memorizedCard = memorizedCard.toDto(),
        recalledCard = recalledCard?.toDto(),
        correct = isCorrect
    )
}