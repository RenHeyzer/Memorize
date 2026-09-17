package dev.renheyzer.memorize.feature.core.statistics.data.repository

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.data.extension.safeApiCall
import dev.renheyzer.memorize.feature.core.cards.data.dto.toDomain
import dev.renheyzer.memorize.feature.core.cards.data.source.remote.CardsRemoteDataSource
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.numbers.data.dto.toDomain
import dev.renheyzer.memorize.feature.core.numbers.data.source.remote.NumbersRemoteDataSource
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class StatisticsRepositoryImpl(
    private val defaultDispatcher: CoroutineContext,
    private val numbersRemoteDataSource: NumbersRemoteDataSource,
    private val cardsRemoteDataSource: CardsRemoteDataSource
) : StatisticsRepository {

    override suspend fun getNumbersResults(): Either<NetworkError, List<NumbersResult>> =
        safeApiCall {
            val results = numbersRemoteDataSource.fetchGameResults()
            withContext(defaultDispatcher) {
                results.map { it.toDomain() }
            }
        }

    override suspend fun getCardsResults(): Either<NetworkError, List<CardsResult>> =
        safeApiCall {
            val results = cardsRemoteDataSource.fetchGameResults()
            withContext(defaultDispatcher) {
                results.map { it.toDomain() }
            }
        }
}