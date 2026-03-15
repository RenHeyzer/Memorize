package dev.renheyzer.memorize.core.components.core.numbers.result

import dev.renheyzer.memorize.core.components.core.numbers.result.store.ResultsUiState
import kotlinx.coroutines.flow.StateFlow

interface ResultsComponent {
    val uiState: StateFlow<ResultsUiState>

    fun onGoHomeClick()
    fun onPlayAgainClick()
}