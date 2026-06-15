package dev.renheyzer.memorize.feature.core.cards.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CardsAnswer(
    val values: List<Card?>
) {
    companion object {
        fun empty(size: Int) {
            require(size > 0) { "At least one answer is required" }
            CardsAnswer(
                values = List(size) { null }
            )
        }
    }
}