package dev.renheyzer.memorize.core.components.core.numbers.recall

import dev.renheyzer.memorize.core.components.core.numbers.recall.store.RecallUiState
import kotlinx.coroutines.flow.StateFlow

interface RecallComponent {

    val uiState: StateFlow<RecallUiState>
    val timerState: StateFlow<String>

    fun whenUserEnteredAnswer(index: Int, answer: String)
    fun onCompleteClick()
}