package dev.renheyzer.memorize.feature.core.numbers.presentation.store.round

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.onEachSecond
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CalculateRoundResultUseCase
import dev.renheyzer.memorize.feature.core.presenatation.RoundStage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RoundStore(
    mainContext: CoroutineContext,
    private val countdownTimerManager: CountdownTimerManager,
    params: NumbersParam,
    private val currentRound: Int,
    private val task: NumbersTask,
    private val calculateRoundResults: CalculateRoundResultUseCase
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(
        NumbersRoundState(
            currentRound = currentRound,
            columns = params.columns,
            rows = params.rows,
            numbers = task.numbers,
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _timerState = MutableStateFlow("")
    val timerState = _timerState.asStateFlow()

    private val _actions = Channel<NumbersRoundAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        _uiState.update { state ->
            state.copy(
                startedAtMillis = System.currentTimeMillis()
            )
        }

        countdownTimerManager.setDuration(params.memorizationTimeSeconds * 1000L)
        observeTimer()
    }

    private fun observeTimer() {
        countdownTimerManager.onEachSecond(scope) { timerText ->
            _timerState.update { timerText }
        }

        countdownTimerManager.events
            .onEach { value ->
                if (value is CountdownTimerManager.TimerEvent.Finished) {
                    if (_uiState.value.roundStage == RoundStage.RECALL) return@onEach
                    _uiState.update { state -> state.copy(roundStage = RoundStage.RECALL) }
                }
            }.launchIn(scope)
    }

    fun startTimer() {
        countdownTimerManager.start(scope)
    }

    fun pauseTimer() {
        countdownTimerManager.pause()
    }

    fun onIntent(intent: NumbersRoundIntent) {
        when (intent) {
            is NumbersRoundIntent.OnUserAnswerChanged -> changeUserAnswer(
                index = intent.index,
                answer = intent.answer
            )

            NumbersRoundIntent.StageCompleted -> onStageCompleted()

            NumbersRoundIntent.BackClicked -> _uiState.update { state ->
                state.copy(isShowExitDialog = true)
            }

            NumbersRoundIntent.ExitConfirmed -> {
                _uiState.update { state -> state.copy(isShowExitDialog = false) }
                scope.launch {
                    _actions.send(NumbersRoundAction.NavigateHome)
                }
            }

            NumbersRoundIntent.ExitDismissed -> _uiState.update { state ->
                state.copy(isShowExitDialog = false)
            }
        }
    }

    private fun changeUserAnswer(index: Int, answer: String) {
        val answerOrNull = answer.ifEmpty { null }

        _uiState.update { state ->
            val newAnswers = state.answers.toMutableList().apply {
                set(index = index, answerOrNull?.toInt())
            }

            state.copy(
                answers = newAnswers,
                isAllFilled = newAnswers.none { it == null }
            )
        }
    }

    private fun onStageCompleted(roundResult: RoundResult? = null) {
        when (_uiState.value.roundStage) {
            RoundStage.MEMORIZATION -> onMemorizationFinished()
            RoundStage.RECALL -> onRecallFinished()
            else -> {
                val result = requireNotNull(roundResult) {
                    "Round result cannot be null when round finished"
                }
                scope.launch {
                    _actions.send(NumbersRoundAction.FinishRound(roundResult = result))
                }
            }
        }
    }

    private fun onMemorizationFinished() {
        if (_uiState.value.roundStage == RoundStage.RECALL) return
        _uiState.update { state ->
            state.copy(
                roundStage = RoundStage.RECALL,
                recallStartedAtMillis = System.currentTimeMillis()
            )
        }

        countdownTimerManager.stop()
    }

    private fun onRecallFinished() {
        if (_uiState.value.roundStage == RoundStage.CLEARED || _uiState.value.roundStage == RoundStage.GAME_OVER) return

        val numbersAnswer = NumbersAnswer(values = uiState.value.answers)

        val roundResult = calculateRoundResults(
            task = task,
            answer = numbersAnswer,
            startedAtMillis = requireNotNull(uiState.value.startedAtMillis) {
                "startedAtMillis cannot be null when finishing recall"
            },
            recallStartedAtMillis = requireNotNull(uiState.value.recallStartedAtMillis) {
                "recallStartedAtMillis cannot be null when finishing recall"
            },
            completedAtMillis = System.currentTimeMillis()
        )

        if (roundResult.isCleared) {
            _uiState.update { state -> state.copy(roundStage = RoundStage.CLEARED) }
            onStageCompleted(roundResult)
        } else {
            _uiState.update { state -> state.copy(roundStage = RoundStage.GAME_OVER) }
            onStageCompleted(roundResult)
        }
    }

    override fun onDestroy() {
        countdownTimerManager.stop()
        scope.cancel()
    }
}

data class NumbersRoundState(
    val currentRound: Int = 0,
    val columns: Int = 0,
    val rows: Int = 0,
    val numbers: List<Int> = emptyList(),
    val answers: List<Int?> = emptyList(),
    val isAllFilled: Boolean = false,
    val roundStage: RoundStage = RoundStage.MEMORIZATION,
    val isShowExitDialog: Boolean = false,
    val startedAtMillis: Long? = null,
    val recallStartedAtMillis: Long? = null,
    val completedAtMillis: Long? = null,
)

sealed interface NumbersRoundIntent {
    data class OnUserAnswerChanged(val index: Int, val answer: String) : NumbersRoundIntent
    data object StageCompleted : NumbersRoundIntent
    data object BackClicked : NumbersRoundIntent
    data object ExitConfirmed : NumbersRoundIntent
    data object ExitDismissed : NumbersRoundIntent
}

sealed interface NumbersRoundAction {
    data object NavigateHome : NumbersRoundAction
    data class FinishRound(val roundResult: RoundResult) : NumbersRoundAction
}