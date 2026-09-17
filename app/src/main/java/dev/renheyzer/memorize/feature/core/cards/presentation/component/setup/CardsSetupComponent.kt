package dev.renheyzer.memorize.feature.core.cards.presentation.component.setup

import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupState
import kotlinx.coroutines.flow.StateFlow

interface CardsSetupComponent {
    val uiState: StateFlow<CardsSetupState>

    fun onIntent(intent: CardsSetupIntent)
}