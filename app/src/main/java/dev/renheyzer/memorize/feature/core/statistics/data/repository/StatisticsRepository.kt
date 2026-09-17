package dev.renheyzer.memorize.feature.core.statistics.data.repository

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult

interface StatisticsRepository {

    suspend fun getNumbersResults(): Either<NetworkError, List<NumbersResult>>
    suspend fun getCardsResults(): Either<NetworkError, List<CardsResult>>
}