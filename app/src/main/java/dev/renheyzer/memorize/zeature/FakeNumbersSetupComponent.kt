package dev.renheyzer.memorize.zeature

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeNumbersSetupComponent : NumbersSetupComponent {
    override val uiState: StateFlow<NumbersSetupUiState> = MutableStateFlow(NumbersSetupUiState())

    override fun onEvent(event: NumbersSetupEvent) = Unit
}