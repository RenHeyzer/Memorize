package dev.renheyzer.memorize.feature.core.cards.presentation.store.results

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ResultsStore(
    mainContext: CoroutineContext,
    results: CardsResult
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(ResultsUiState(results = results))
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<ResultsAction>()
    val actions = _actions.receiveAsFlow()

    fun onIntent(intent: ResultsIntent) {
        when (intent) {
            ResultsIntent.OnCompleteClicked -> {
                if (_uiState.value.isFinished) return
                _uiState.update { it.copy(isFinished = true) }

                scope.launch {
                    _actions.send(ResultsAction.CompleteRequested)
                }
            }

            ResultsIntent.OnPlayAgainClicked -> {
                if (_uiState.value.isFinished) return
                _uiState.update { it.copy(isFinished = true) }

                scope.launch {
                    _actions.send(ResultsAction.PlayAgainRequested)
                }
            }
        }
    }
}

data class ResultsUiState(
    val results: CardsResult,
    val isFinished: Boolean = false
)

sealed interface ResultsIntent {
    data object OnPlayAgainClicked : ResultsIntent
    data object OnCompleteClicked : ResultsIntent
}

sealed interface ResultsAction {
    data object PlayAgainRequested : ResultsAction
    data object CompleteRequested : ResultsAction
}
