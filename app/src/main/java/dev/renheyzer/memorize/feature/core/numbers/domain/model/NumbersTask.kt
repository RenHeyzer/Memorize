package dev.renheyzer.memorize.feature.core.numbers.domain.model

import dev.renheyzer.memorize.core.models.GameRandomConfig
import kotlinx.serialization.Serializable

@Serializable
data class NumbersTask(
    val numbers: List<Int>,
    val randomConfig: GameRandomConfig
)