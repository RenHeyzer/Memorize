package dev.renheyzer.memorize.feature.core.numbers.domain.usecase

import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask

class CalculateNumbersResultUseCase {

    operator fun invoke(
        params: NumbersParam,
        task: NumbersTask,
        answer: NumbersAnswer,
        startedAtMillis: Long,
        recallStartedAtMillis: Long,
        completedAtMillis: Long,
    ): NumbersResult {
        val details = task.numbers.mapIndexed { i, number ->
            val answer = answer.values.getOrNull(i)
            AnswerResult(
                index = i,
                number = number,
                answer = answer,
            )
        }

        return NumbersResult(
            params = params,
            details = details,
            startedAtMillis = startedAtMillis,
            recallStartedAtMillis = recallStartedAtMillis,
            completedAtMillis = completedAtMillis
        )
    }
}