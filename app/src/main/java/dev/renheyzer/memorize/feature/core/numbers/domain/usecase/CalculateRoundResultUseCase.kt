package dev.renheyzer.memorize.feature.core.numbers.domain.usecase

import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import java.util.UUID

class CalculateRoundResultUseCase {

    operator fun invoke(
        task: NumbersTask,
        answer: NumbersAnswer,
        startedAtMillis: Long,
        recallStartedAtMillis: Long,
        completedAtMillis: Long,
    ): RoundResult {
        val details = task.numbers.mapIndexed { i, number ->
            val answer = answer.values.getOrNull(i)
            AnswerResult(
                index = i,
                number = number,
                answer = answer,
            )
        }

        return RoundResult(
            id = UUID.randomUUID().toString(),
            details = details,
            randomConfig = task.randomConfig,
            startedAtMillis = startedAtMillis,
            recallStartedAtMillis = recallStartedAtMillis,
            completedAtMillis = completedAtMillis
        )
    }
}