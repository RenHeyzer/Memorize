package dev.renheyzer.memorize.feature.core.cards.presentation.store.results

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.cards.domain.model.AnswerResult
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
import kotlin.math.ceil

class ResultsStore(
    mainContext: CoroutineContext,
    private val results: CardsResult
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<ResultsAction>()
    val actions = _actions.receiveAsFlow()

    init {
        mapCardsResultToUi()
    }

    private fun mapCardsResultToUi() {
        _uiState.update {
            it.copy(
                details = results.details,
                correctCount = results.correctCount,
                totalCount = results.totalCount,
                scorePercentage = results.accuracy
            )
        }
    }

    fun onIntent(intent: ResultsIntent) {
        when (intent) {
            ResultsIntent.OnCompleteClicked -> {
                if (_uiState.value.isFinished) return
                _uiState.update { it.copy(isFinished = true) }

                scope.launch {
                    _actions.send(ResultsAction.FinishResults)
                }
            }

            ResultsIntent.OnPlayAgainClicked -> {
                if (_uiState.value.isFinished) return
                _uiState.update { it.copy(isFinished = true) }

                scope.launch {
                    _actions.send(ResultsAction.PlayAgain)
                }
            }
        }
    }
}

data class ResultsUiState(
    val details: List<AnswerResult> = emptyList(),
    val correctCount: Int = 0,
    val totalCount: Int = 0,
    val scorePercentage: Float = 0f,
    val itemPerPage: Int = 3,
    val isFinished: Boolean = false
) {
    val pageCount: Int
        get() = ceil(details.size.toDouble() / itemPerPage).toInt()
}

sealed interface ResultsIntent {
    data object OnPlayAgainClicked : ResultsIntent
    data object OnCompleteClicked : ResultsIntent
}

sealed interface ResultsAction {
    data object PlayAgain : ResultsAction
    data object FinishResults : ResultsAction
}
