package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall

import dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall.RecallComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeRecallComponent : RecallComponent {

    override val uiState: StateFlow<RecallUiState> = MutableStateFlow(
        RecallUiState(
            answers = List(27) { null }
        )
    )
    override val timerState: StateFlow<String> = MutableStateFlow("01:32")

    override fun onIntent(intent: RecallIntent) = Unit
}