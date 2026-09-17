package dev.renheyzer.memorize.feature.core.statistics.domain.usecase

import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult

class CalculateOverallAverageAccuracyUseCase() {

    operator fun invoke(
        roundResults: List<RoundResult>,
        cardsResults: List<CardsResult>
    ): Float {
        if (roundResults.isEmpty() && cardsResults.isEmpty()) {
            return 0.0f
        }

        val numbersAverage = roundResults.map { it.accuracy }.average()
        val cardsAverage = cardsResults.map { it.accuracy }.average()

        return ((numbersAverage + cardsAverage) / 2).toFloat()
    }
}