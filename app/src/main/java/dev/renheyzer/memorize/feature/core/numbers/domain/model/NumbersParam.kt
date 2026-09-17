package dev.renheyzer.memorize.feature.core.numbers.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NumbersParam(
    val rounds: Int,
    val columns: Int,
    val rows: Int,
    val mode: NumbersMode,
    val memorizationTimeSeconds: Int,
    val recallTimeSeconds: Int? = null
) {
    init {
        require(rounds > 0) {
            "Numbers rounds must be greater than zero"
        }
        require(columns >= 2) {
            "Numbers columns must be greater than or equal to two"
        }
        require(rows >= 2) {
            "Numbers rows must be greater than or equal to two"
        }
        require(memorizationTimeSeconds > 0) {
            "Memorization time must be greater than zero"
        }
    }

    val quantity: Int = columns * rows * rounds
}