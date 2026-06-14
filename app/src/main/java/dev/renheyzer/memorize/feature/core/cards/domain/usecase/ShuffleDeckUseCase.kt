package dev.renheyzer.memorize.feature.core.cards.domain.usecase

import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import java.util.Random

class ShuffleDeckUseCase {
    operator fun invoke(deck: List<Card>, random: Random? = null): List<Card> {
        if (deck.isEmpty()) return emptyList()

        return if (random != null) {
            deck.shuffled(random)
        } else {
            deck.shuffled()
        }
    }
}