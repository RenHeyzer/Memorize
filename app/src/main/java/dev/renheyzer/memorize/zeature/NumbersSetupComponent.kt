package dev.renheyzer.memorize.zeature

import kotlinx.coroutines.flow.StateFlow

interface NumbersSetupComponent {
    val uiState: StateFlow<NumbersSetupUiState>

    fun onEvent(event: NumbersSetupEvent)
}