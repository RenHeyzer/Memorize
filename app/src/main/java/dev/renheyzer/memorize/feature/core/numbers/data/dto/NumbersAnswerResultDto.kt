package dev.renheyzer.memorize.feature.core.numbers.data.dto

import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import kotlinx.serialization.Serializable

@Serializable
data class AnswerResultDto(
    val index: Int = 0,
    val number: Int = 0,
    val answer: Int? = null,
    val correct: Boolean = false
)

fun AnswerResultDto.toDomain(): AnswerResult {
    return AnswerResult(
        index = index,
        number = number,
        answer = answer
    )
}

fun AnswerResult.toDto(): AnswerResultDto {
    return AnswerResultDto(
        index = index,
        number = number,
        answer = answer,
        correct = isCorrect
    )
}