package dev.renheyzer.memorize.core.models

import kotlinx.serialization.Serializable

@Serializable
data class GameRandomConfig(
    val seed: Long,
    val algorithmVersion: Int = 1
)
