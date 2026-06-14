package dev.renheyzer.memorize.feature.core.numbers.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NumbersAnswer(
    val values: List<Int?>
) {
    companion object {
        fun empty(size: Int) {
            require(size > 0) { "At least one answer is required" }
            NumbersAnswer(
                values = List(size) { null }
            )
        }
    }
}