package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall

import dev.renheyzer.memorize.core.components.core.numbers.recall.RecallComponent
import dev.renheyzer.memorize.core.components.core.numbers.recall.store.RecallUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeRecallComponent : RecallComponent {
    private val answers: MutableList<Int?> =
        MutableList(27) { null }

    override val uiState: StateFlow<RecallUiState> = MutableStateFlow(
        RecallUiState(
            pagedAnswers = answers.chunked(9)
        )
    )
    override val timerState: StateFlow<String> = MutableStateFlow("01:32")

    override fun whenUserEnteredAnswer(index: Int, answer: Int) = Unit

    override fun onCompleteClick() = Unit
}