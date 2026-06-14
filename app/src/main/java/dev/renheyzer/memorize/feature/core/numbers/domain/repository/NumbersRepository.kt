package dev.renheyzer.memorize.feature.core.numbers.domain.repository

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult

interface NumbersRepository {

    suspend fun saveGameResult(result: NumbersResult): Either<AppError, Unit>

    suspend fun getGameResult(): Either<AppError, NumbersResult>
}