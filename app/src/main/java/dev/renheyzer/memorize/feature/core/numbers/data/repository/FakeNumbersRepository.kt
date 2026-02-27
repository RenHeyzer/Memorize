package dev.renheyzer.memorize.feature.core.numbers.data.repository

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.feature.core.numbers.domain.model.GameResult
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import kotlinx.coroutines.delay

class FakeNumbersRepository : NumbersRepository {

    private var cachedResult: GameResult? = null

    override suspend fun saveGameResult(result: GameResult): Either<AppError, Unit> {
        cachedResult = result
        delay(1000)
        return Either.Right(Unit)
    }

    override suspend fun getGameResult(): Either<AppError, GameResult> {
        delay(1000)
        return if (cachedResult != null) {
            Either.Right(cachedResult!!)
        } else {
            Either.Left(NetworkError.NotFound)
        }
    }
}