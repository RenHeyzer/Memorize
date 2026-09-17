package dev.renheyzer.memorize.feature.core.cards.data.dto

import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: Int = 0,
    val suit: CardSuit? = null,
    val rank: CardRank? = null,
    val value: String = ""
)

fun CardDto.toDomain(): Card {
    return Card(
        id = id,
        suit = suit ?: throw IllegalArgumentException("Suit cannot be null"),
        rank = rank ?: throw IllegalArgumentException("Rank cannot be null")
    )
}

fun Card.toDto(): CardDto {
    return CardDto(
        id = id,
        suit = suit,
        rank = rank,
        value = value
    )
}