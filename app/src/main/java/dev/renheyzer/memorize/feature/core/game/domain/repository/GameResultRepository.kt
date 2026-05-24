package dev.renheyzer.memorize.feature.core.game.domain.repository

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.feature.core.game.domain.model.GameResult
import dev.renheyzer.memorize.feature.core.game.domain.model.GameSessionId

interface GameResultRepository {

    suspend fun saveResult(result: GameResult): Either<AppError, Unit>

    suspend fun isResultSaved(sessionId: GameSessionId): Boolean
}