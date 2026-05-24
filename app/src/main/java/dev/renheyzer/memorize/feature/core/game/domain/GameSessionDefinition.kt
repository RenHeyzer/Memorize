package dev.renheyzer.memorize.feature.core.game.domain

import dev.renheyzer.memorize.feature.core.game.domain.model.GameResult
import dev.renheyzer.memorize.feature.core.game.domain.model.GameSessionId
import dev.renheyzer.memorize.feature.core.game.domain.model.GameType

interface GameSessionDefinition<Params, Task, Answer, Result> {

    val gameType: GameType

    suspend fun createTask(params: Params): Task

    fun createEmptyAnswer(task: Task): Answer

    suspend fun checkAnswer(
        sessionId: GameSessionId,
        task: Task,
        answer: Answer,
        startedAtMillis: Long,
        completedAtMillis: Long,
    ): Result

    fun commonResult(result: Result): GameResult
}