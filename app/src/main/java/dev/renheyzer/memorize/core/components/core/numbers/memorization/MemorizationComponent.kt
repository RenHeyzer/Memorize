package dev.renheyzer.memorize.core.components.core.numbers.memorization

import dev.renheyzer.memorize.core.components.core.numbers.memorization.store.MemorizationUiState
import kotlinx.coroutines.flow.StateFlow

interface MemorizationComponent {

    val uiState: StateFlow<MemorizationUiState>
    val timerState: StateFlow<String>

    data class Params(
        val quantity: Int,
        val time: Long,
        val isRandom: Boolean
    )

    fun onCompleteClick()
}