package dev.renheyzer.memorize.feature.core.cards.presentation.component.recall

import dev.renheyzer.memorize.feature.core.cards.presentation.store.recall.RecallIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.recall.RecallUiState
import kotlinx.coroutines.flow.StateFlow

interface RecallComponent {
    val uiState: StateFlow<RecallUiState>
    val timerState: StateFlow<String>

    fun onIntent(intent: RecallIntent)
}