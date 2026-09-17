package dev.renheyzer.memorize.feature.core.numbers.domain.repository

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult

interface NumbersRepository {

    suspend fun saveGameResult(result: NumbersResult): Either<NetworkError, Unit>
}