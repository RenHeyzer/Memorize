package dev.renheyzer.memorize.feature.core.cards.domain.usecase

import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsTask
import java.util.Random

class ShuffleDeckUseCase {
    operator fun invoke(deck: List<Card>, random: Random? = null): CardsTask {
        require(deck.isNotEmpty()) {
            "Deck cannot be empty"
        }

        val deck = if (random != null) {
            deck.shuffled(random)
        } else {
            deck.shuffled()
        }
        return CardsTask(deck)
    }
}