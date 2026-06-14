package dev.renheyzer.memorize.feature.core.cards.domain.usecase

import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit

class GenerateOrderedDeckUseCase {
    operator fun invoke(suits: List<CardSuit>): List<Card> {
        if (suits.isEmpty()) return emptyList()

        val sortedSuits = suits.sortedBy { it.sortOrder }

        return sortedSuits.flatMap { suit ->
            CardRank.entries.map { rank ->
                Card(
                    suit = suit,
                    rank = rank
                )
            }
        }
    }
}