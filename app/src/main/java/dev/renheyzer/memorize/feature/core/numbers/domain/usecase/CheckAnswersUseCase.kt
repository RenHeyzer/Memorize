package dev.renheyzer.memorize.feature.core.numbers.domain.usecase

import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.GameResult

class CheckAnswersUseCase {

    operator fun invoke(numbers: List<Int>, answers: List<Int>, isRandom: Boolean): GameResult {
        val details = numbers.mapIndexed { i, number ->
            val answer = answers.getOrNull(i)
            AnswerResult(
                id = i,
                number = number,
                answer = answer,
                isCorrect = number == answer
            )
        }

        val correctCount = details.count { it.isCorrect }
        val totalCount = numbers.size
        val scorePercentage = if (totalCount > 0) {
            (correctCount * 100f) / totalCount
        } else 0f

        return GameResult(
            details = details,
            isRandom = isRandom,
            correctCount = correctCount,
            totalCount = totalCount,
            scorePercentage = scorePercentage,
        )
    }
}