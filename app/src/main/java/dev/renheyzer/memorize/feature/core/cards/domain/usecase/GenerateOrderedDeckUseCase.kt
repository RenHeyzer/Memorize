package dev.renheyzer.memorize.feature.core.cards.domain.usecase

import CardRank
import CardSuit
import PlayingCard

class GenerateOrderedDeckUseCase {
    operator fun invoke(suits: List<CardSuit>): List<PlayingCard> {
        if (suits.isEmpty()) return emptyList()

        val sortedSuits = suits.sortedBy { it.sortOrder }

        return sortedSuits.flatMap { suit ->
            CardRank.entries.map { rank ->
                PlayingCard(
                    suit = suit,
                    rank = rank
                )
            }
        }
    }
}