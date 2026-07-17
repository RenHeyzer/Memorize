package dev.renheyzer.memorize.feature.core.cards.domain.model

enum class CardSuit(val suit: String, val baseId: Int, val sortOrder: Int) {
    CLUBS("♣️", 100, 1),
    HEARTS("♥️", 200, 2),
    SPADES("♠️", 300, 3),
    DIAMONDS("♦️", 400, 4)
}

