package dev.renheyzer.memorize.feature.core.cards.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CardsParam(
    val suits: List<CardSuit>,
    val memorizationTimeSeconds: Int,
    val recallTimeSeconds: Int
) {
    init {
        require(suits.isNotEmpty()) { "Suits cannot be empty" }
        require(memorizationTimeSeconds > 0) { "Memorization time must be greater than 0" }
    }

    val quantity = suits.size * CardRank.entries.size
}