package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupState
import kotlinx.coroutines.flow.StateFlow

interface NumbersSetupComponent {
    val uiState: StateFlow<NumbersSetupState>

    fun onIntent(intent: NumbersSetupIntent)
}