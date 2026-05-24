package dev.renheyzer.memorize.feature.core.game.domain.usecase

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.feature.core.game.domain.model.GameResult
import dev.renheyzer.memorize.feature.core.game.domain.repository.GameResultRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SaveGameResultOnceUseCase(
    private val repository: GameResultRepository,
) {
    private val mutex = Mutex()

    suspend operator fun invoke(result: GameResult): Either<AppError, Unit> {
        return mutex.withLock {
            if (repository.isResultSaved(result.sessionId)) {
                Either.Right(Unit)
            } else {
                repository.saveResult(result)
            }
        }
    }
}