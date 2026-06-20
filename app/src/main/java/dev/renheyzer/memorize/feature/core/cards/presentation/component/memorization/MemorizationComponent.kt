package dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization

import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationUiState
import kotlinx.coroutines.flow.StateFlow

interface MemorizationComponent {
    val uiState: StateFlow<MemorizationUiState>

    val timerState: StateFlow<String>

    fun onIntent(intent: MemorizationIntent)
}