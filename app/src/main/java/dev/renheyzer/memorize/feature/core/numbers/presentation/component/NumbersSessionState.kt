package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import kotlinx.serialization.Serializable

@Serializable
data class NumbersSessionState(
    val params: NumbersParam? = null,
    val task: NumbersTask? = null,
    val answer: NumbersAnswer? = null,
    val result: NumbersResult? = null,
    val startedAtMillis: Long? = null,
    val recallStartedAtMillis: Long? = null,
    val completedAtMillis: Long? = null,
)