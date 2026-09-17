package dev.renheyzer.memorize.feature.core.cards.data.dto

import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import kotlinx.serialization.Serializable

@Serializable
data class CardsParamDto(
    val suits: List<CardSuit> = emptyList(),
    val quantity: Int = 0,
    val memorizationTimeSeconds: Int = 0,
    val recallTimeSeconds: Int = 0
)

fun CardsParamDto.toDomain(): CardsParam {
    return CardsParam(
        suits = suits,
        memorizationTimeSeconds = memorizationTimeSeconds,
        recallTimeSeconds = recallTimeSeconds
    )
}

fun CardsParam.toDto(): CardsParamDto {
    return CardsParamDto(
        suits = suits,
        quantity = quantity,
        memorizationTimeSeconds = memorizationTimeSeconds,
        recallTimeSeconds = recallTimeSeconds
    )
}