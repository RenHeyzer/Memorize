package dev.renheyzer.memorize.feature.core.numbers.data.repository

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.data.extension.safeApiCall
import dev.renheyzer.memorize.feature.core.numbers.data.dto.toDto
import dev.renheyzer.memorize.feature.core.numbers.data.source.remote.NumbersRemoteDataSource
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository

class NumbersRepositoryImpl(
    private val remoteDataSource: NumbersRemoteDataSource,
) : NumbersRepository {

    override suspend fun saveGameResult(result: NumbersResult): Either<NetworkError, Unit> =
        safeApiCall {
            remoteDataSource.saveGameResults(result.toDto())
        }
}