package dev.renheyzer.memorize.feature.core.statistics.domain.usecase

import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult

class DetermineBestNumbersResults() {

    operator fun invoke(
        roundResults: List<RoundResult>,
    ): RoundResult {
        if (roundResults.isEmpty()) {
            throw IllegalArgumentException("Numbers results cannot be empty")
        }

        var theBest = roundResults[0]

        for (result in roundResults) {
            when {
                theBest.accuracy == result.accuracy -> {
                    if (result.totalCount > theBest.totalCount) {
                        theBest = result
                    }
                }
                theBest.accuracy > result.accuracy -> {

                }
            }
        }
        return theBest
    }
}