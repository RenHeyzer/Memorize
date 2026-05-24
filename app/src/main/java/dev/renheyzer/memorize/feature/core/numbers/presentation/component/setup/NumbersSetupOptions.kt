package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

data class NumbersSetupOptions(
    val quantity: Int,
    val rememberTime: Long,
    val recallTime: Long,
    val isBinary: Boolean = true
)
