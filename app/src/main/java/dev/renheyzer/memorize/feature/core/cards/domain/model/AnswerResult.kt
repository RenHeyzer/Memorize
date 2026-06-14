package dev.renheyzer.memorize.feature.core.cards.domain.model

data class AnswerResult(
    val id: Int,
    val memorizedCard: Card,
    val recalledCard: Card? = null,
) {
    val isCorrect: Boolean = memorizedCard.id == recalledCard?.id
}