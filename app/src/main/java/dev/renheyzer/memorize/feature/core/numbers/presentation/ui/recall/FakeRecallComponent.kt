package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall

import dev.renheyzer.memorize.core.components.core.numbers.recall.RecallComponent
import dev.renheyzer.memorize.core.components.core.numbers.recall.store.RecallUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeRecallComponent : RecallComponent {

    override val uiState: StateFlow<RecallUiState> = MutableStateFlow(
        RecallUiState(
            answers = List(27) { null }
        )
    )
    override val timerState: StateFlow<String> = MutableStateFlow("01:32")

    override fun whenUserEnteredAnswer(
        index: Int,
        answer: String
    ) = Unit

    override fun onCompleteClick() = Unit
}