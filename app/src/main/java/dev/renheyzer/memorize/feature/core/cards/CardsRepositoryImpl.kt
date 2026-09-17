package dev.renheyzer.memorize.feature.core.cards

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.data.extension.safeApiCall
import dev.renheyzer.memorize.feature.core.cards.data.dto.toDto
import dev.renheyzer.memorize.feature.core.cards.data.source.remote.CardsRemoteDataSource
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult

class CardsRepositoryImpl(
    private val remoteDataSource: CardsRemoteDataSource,
) : CardsRepository {

    override suspend fun saveGameResult(result: CardsResult): Either<NetworkError, Unit> =
        safeApiCall {
            remoteDataSource.saveGameResult(result.toDto())
        }
}