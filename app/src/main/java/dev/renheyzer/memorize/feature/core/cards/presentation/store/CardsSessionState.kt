package dev.renheyzer.memorize.feature.core.cards.presentation.store

import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsTask
import kotlinx.serialization.Serializable

@Serializable
data class CardsSessionState(
    val params: CardsParam? = null,
    val orderedDeck: List<Card>? = null,
    val task: CardsTask? = null,
    val answer: CardsAnswer? = null,
    val result: CardsResult? = null,
    val startedAtMillis: Long? = null,
    val recallStartedAtMillis: Long? = null,
    val completedAtMillis: Long? = null
)
