package dev.renheyzer.memorize.zeature

data class NumbersSetupOptions(
    val quantity: Int,
    val rememberTime: Long,
    val recallTime: Long,
    val isBinary: Boolean = true
)
