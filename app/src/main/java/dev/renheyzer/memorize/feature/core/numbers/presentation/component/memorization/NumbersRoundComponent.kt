package dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundState
import kotlinx.coroutines.flow.StateFlow

interface NumbersRoundComponent {

    val uiState: StateFlow<NumbersRoundState>
    val timerState: StateFlow<String>

    fun onIntent(intent: NumbersRoundIntent)
}