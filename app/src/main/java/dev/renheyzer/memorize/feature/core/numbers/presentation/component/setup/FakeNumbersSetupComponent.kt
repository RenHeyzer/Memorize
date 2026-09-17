package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeNumbersSetupComponent : NumbersSetupComponent {
    override val uiState: StateFlow<NumbersSetupState> = MutableStateFlow(NumbersSetupState())

    override fun onIntent(event: NumbersSetupIntent) = Unit
}