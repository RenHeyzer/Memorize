package dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall

import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallUiState
import kotlinx.coroutines.flow.StateFlow

interface RecallComponent {

    val uiState: StateFlow<RecallUiState>
    val timerState: StateFlow<String>

    fun whenUserEnteredAnswer(index: Int, answer: String)
    fun onCompleteClick()
}