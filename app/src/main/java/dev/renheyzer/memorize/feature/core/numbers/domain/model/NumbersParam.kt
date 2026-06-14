package dev.renheyzer.memorize.feature.core.numbers.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NumbersParam(
    val quantity: Int,
    val mode: NumbersMode,
    val memorizationTimeSeconds: Int,
    val recallTimeSeconds: Int
) {
    init {
        require(quantity > 0) {
            "Numbers quantity must be greater than zero"
        }
        require(memorizationTimeSeconds > 0) {
            "Memorization time must be greater than zero"
        }
        require(recallTimeSeconds > 0) {
            "Recall time must be greater than zero"
        }
    }
}