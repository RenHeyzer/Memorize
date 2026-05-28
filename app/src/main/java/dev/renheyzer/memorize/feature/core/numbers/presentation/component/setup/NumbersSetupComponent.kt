package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupEvent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupUiState
import kotlinx.coroutines.flow.StateFlow

interface NumbersSetupComponent {
    val uiState: StateFlow<NumbersSetupUiState>

    fun onEvent(event: NumbersSetupEvent)
}