package dev.renheyzer.memorize.feature.core.numbers.presentation.store.result

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.ext.formatMillisAsMMSS
import dev.renheyzer.memorize.core.ui.ext.toPercentString
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.presentation.model.RoundResultUi
import dev.renheyzer.memorize.feature.core.numbers.presentation.model.toRoundResultUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ResultStore(
    mainContext: CoroutineContext,
    private val result: NumbersResult
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(ResultState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<ResultAction>()
    val actions = _actions.receiveAsFlow()

    init {
        mapNumbersResultToUi()
    }

    private fun mapNumbersResultToUi() {
        _uiState.update { state ->
            state.copy(
                rounds = result.params.rounds,
                roundsResult = result.rounds.map { it.toRoundResultUi() },
                correctCount = result.correctCount,
                totalCount = result.totalCount,
                scorePercentage = result.totalAccuracy.toPercentString(),
                totalSpentTime = result.totalSpentMillis.formatMillisAsMMSS()
            )
        }
    }

    fun onIntent(intent: ResultIntent) {
        when (intent) {
            ResultIntent.CompleteClicked -> {
                if (_uiState.value.isFinished) return
                _uiState.update { it.copy(isFinished = true) }

                scope.launch {
                    _actions.send(ResultAction.NavigateHome)
                }
            }

            ResultIntent.PlayAgainClicked -> {
                if (_uiState.value.isFinished) return
                _uiState.update { it.copy(isFinished = true) }

                scope.launch {
                    _actions.send(ResultAction.PlayAgain)
                }
            }
        }
    }
}

data class ResultState(
    val rounds: Int = 0,
    val roundsResult: List<RoundResultUi> = emptyList(),
    val correctCount: Int = 0,
    val totalCount: Int = 0,
    val scorePercentage: String = "",
    val totalSpentTime: String = "",
    val isFinished: Boolean = false
)

sealed interface ResultIntent {
    data object PlayAgainClicked : ResultIntent
    data object CompleteClicked : ResultIntent
}

sealed interface ResultAction {
    data object PlayAgain : ResultAction
    data object NavigateHome : ResultAction
}