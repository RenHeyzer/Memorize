package dev.renheyzer.memorize.feature.core.cards.presentation.component.results

import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsUiState
import kotlinx.coroutines.flow.StateFlow

interface ResultsComponent {

    val uiState: StateFlow<ResultsUiState>

    fun onIntent(intent: ResultsIntent)
}