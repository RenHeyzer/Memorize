package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import kotlinx.serialization.Serializable

@Serializable
data class NumbersSessionState(
    val params: NumbersParam? = null,
    val round: Int = 1,
    val entireTask: NumbersTask? = null,
    val taskPerRound: NumbersTask? = null,
    val rounds: List<RoundResult> = emptyList(),
    val startedAtMillis: Long? = null,
    val completedAtMillis: Long? = null,
    val result: NumbersResult? = null
)