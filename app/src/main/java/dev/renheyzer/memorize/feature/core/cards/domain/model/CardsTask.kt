package dev.renheyzer.memorize.feature.core.cards.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CardsTask(
    val deck: List<Card>
)