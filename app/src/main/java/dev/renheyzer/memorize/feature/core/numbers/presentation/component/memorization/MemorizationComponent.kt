package dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.memorization.MemorizationUiState
import kotlinx.coroutines.flow.StateFlow

interface MemorizationComponent {

    val uiState: StateFlow<MemorizationUiState>
    val timerState: StateFlow<String>

    fun onCompleteClick()
}