package dev.renheyzer.memorize.feature.core.numbers.presentation.model

import dev.renheyzer.memorize.core.ui.ext.formatMillisAsMMSS
import dev.renheyzer.memorize.core.ui.ext.toPercentString
import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult

data class RoundResultUi(
    val id: String = "",
    val details: List<AnswerResult> = emptyList(),
    val totalCount: Int = 0,
    val correctCount: Int = 0,
    val scorePercentage: String = "",
    val memorizeTime: String = "",
    val recallTime: String = "",
    val totalSpentTime: String = ""
)

fun RoundResult.toRoundResultUi(): RoundResultUi {
    return RoundResultUi(
        id = id,
        details = details,
        totalCount = totalCount,
        correctCount = correctCount,
        scorePercentage = accuracy.toPercentString(),
        memorizeTime = memorizationTimeMillis.formatMillisAsMMSS(),
        recallTime = memorizationTimeMillis.formatMillisAsMMSS(),
        totalSpentTime = totalSpentMillis.formatMillisAsMMSS()
    )
}