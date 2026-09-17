package dev.renheyzer.memorize.feature.core.numbers.presentation.store

import android.util.Log
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.common.fold
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.mapper.toUiText
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersSessionState
import dev.renheyzer.memorize.feature.core.numbers.presentation.utils.selectPart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NumbersSessionStore(
    mainContext: CoroutineContext,
    savedState: NumbersSessionState? = null,
    private val generateNumbersUseCase: GenerateNumbersUseCase,
    private val repository: NumbersRepository
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _sessionState = MutableStateFlow(savedState ?: NumbersSessionState())
    val sessionState = _sessionState.asStateFlow()

    private val _actions = Channel<NumbersSessionAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun onIntent(intent: NumbersSessionIntent) {
        when (intent) {
            is NumbersSessionIntent.OnSetupCompleted -> onSetupFinished(params = intent.params)

            is NumbersSessionIntent.OnRoundFinished -> onRoundFinished(roundResult = intent.roundResult)
        }
    }

    private fun onSetupFinished(params: NumbersParam) {
        val entireTask = generateTask(params)
        val firstTask = entireTask.numbers.selectPart(params.quantity)

        _sessionState.update { state ->
            state.copy(
                params = params, entireTask = entireTask, startedAtMillis = System.currentTimeMillis()
            )
        }
    }

    private fun generateTask(params: NumbersParam): NumbersTask {
        val task = generateNumbersUseCase(
            columns = params.columns, rows = params.rows, rounds = params.rounds, mode = params.mode
        )

        return task
    }

    private fun onRoundFinished(roundResult: RoundResult) {
        val state = _sessionState.value
        val params = requireNotNull(state.params) { "Params cannot be null when finishing setup" }
        val task = requireNotNull(state.allTask) { "Task cannot be null when finishing round" }

        if (state.round != params.rounds && roundResult.isCleared) {
            val newRounds = state.rounds.toMutableList().apply { add(roundResult) }.toList()

            _sessionState.update { state ->
                state.copy(
                    allTask = task, rounds = newRounds, round = state.round + 1
                )
            }

            scope.launch {
                _actions.send(NumbersSessionAction.StartNextRound)
            }
        } else {
            sumUpNumbersResult()
        }
    }

    private fun sumUpNumbersResult() {
        val state = _sessionState.value

        require(state.rounds.isNotEmpty()) { "Results cannot be empty when finishing recall" }

        val params = requireNotNull(state.params) { "Params cannot be null when finishing recall" }
        val startedAtMillis = requireNotNull(state.startedAtMillis) {
            "startedAtMillis cannot be null when finishing recall"
        }
        val completedAtMillis = System.currentTimeMillis()

        val result = NumbersResult(
            params = params,
            rounds = state.rounds,
            startedAtMillis = startedAtMillis,
            completedAtMillis = completedAtMillis
        )

        scope.launch {
            repository.saveGameResult(result).fold(onLeft = { e ->
                Log.e("NumbersSessionStore", "onRecallFinished: $e")
                _actions.send(NumbersSessionAction.ShowError(e.toUiText()))
            }, onRight = {
                _sessionState.update { state ->
                    state.copy(
                        completedAtMillis = completedAtMillis, result = result
                    )
                }

                _actions.send(NumbersSessionAction.NavigateToResult)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}

sealed interface NumbersSessionIntent {
    data class OnSetupCompleted(val params: NumbersParam) : NumbersSessionIntent
    data class OnRoundFinished(val roundResult: RoundResult) : NumbersSessionIntent
}

sealed interface NumbersSessionAction {
    data class ShowError(val message: UiText) : NumbersSessionAction
    data object StartNextRound : NumbersSessionAction
    data object NavigateToResult : NumbersSessionAction
}