package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupEvent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeNumbersSetupComponent : NumbersSetupComponent {
    override val uiState: StateFlow<NumbersSetupUiState> = MutableStateFlow(NumbersSetupUiState())

    override fun onEvent(event: NumbersSetupEvent) = Unit
}