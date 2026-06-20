package dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization

import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMemorizationComponent : MemorizationComponent {
    override val uiState: StateFlow<MemorizationUiState> = MutableStateFlow(MemorizationUiState())
    override val timerState: StateFlow<String> = MutableStateFlow("00:00")

    override fun onIntent(intent: MemorizationIntent) = Unit
}