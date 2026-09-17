package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization

import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.NumbersRoundComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class FakeRoundComponent : NumbersRoundComponent {
    override val uiState: StateFlow<NumbersRoundState> = MutableStateFlow(
        NumbersRoundState(
            numbers = List(27) { Random.nextInt(28) },
        )
    )
    override val timerState: StateFlow<String> = MutableStateFlow("01:32")

    override fun onIntent(intent: NumbersRoundIntent) = Unit
}