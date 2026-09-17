package dev.renheyzer.memorize.feature.core.cards.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val suit: CardSuit,
    val rank: CardRank,
    val id: Int = suit.baseId + rank.rankId
) {
    val value: String = "${suit.suit}${rank.value}"
}