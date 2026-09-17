package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultState
import kotlinx.coroutines.flow.StateFlow

interface ResultComponent {
    val uiState: StateFlow<ResultState>

    fun onIntent(intent: ResultIntent)
}