package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsUiState
import kotlinx.coroutines.flow.StateFlow

interface ResultsComponent {
    val uiState: StateFlow<ResultsUiState>

    fun onIntent(intent: ResultsIntent)
}