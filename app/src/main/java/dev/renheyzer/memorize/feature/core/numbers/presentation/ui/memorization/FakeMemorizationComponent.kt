package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization

import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.MemorizationComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.memorization.MemorizationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class FakeMemorizationComponent : MemorizationComponent {
    override val uiState: StateFlow<MemorizationUiState> = MutableStateFlow(
        MemorizationUiState(
            numbers = List(27) { Random.nextInt(28) },
            quantity = 27,
            isRandom = true,
        )
    )
    override val timerState: StateFlow<String> = MutableStateFlow("01:32")

    override fun onCompleteClick() = Unit
}