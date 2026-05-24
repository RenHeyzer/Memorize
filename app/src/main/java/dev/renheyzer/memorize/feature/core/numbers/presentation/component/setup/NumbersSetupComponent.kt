package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import kotlinx.coroutines.flow.StateFlow
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupUiState
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupEvent

interface NumbersSetupComponent {
    val uiState: StateFlow<NumbersSetupUiState>

    fun onEvent(event: NumbersSetupEvent)
}