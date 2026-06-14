package dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.onEachSecond
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
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
import kotlin.math.ceil

class RecallStore(
    mainContext: CoroutineContext,
    private val countdownTimerManager: CountdownTimerManager,
    private val params: NumbersParam
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _recallState = MutableStateFlow(
        RecallUiState(
            answers = List(params.quantity) { null }
        )
    )
    val recallState = _recallState.asStateFlow()

    private val _timerState = MutableStateFlow("")
    val timerState = _timerState.asStateFlow()

    private val _actions = Channel<RecallAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        countdownTimerManager.setDuration(params.recallTimeSeconds * 1000L)

        observeTimer()
        observeTimerEvents()
    }

    fun startTimer() {
        countdownTimerManager.start(scope)
    }

    fun pauseTimer() {
        countdownTimerManager.pause()
    }

    private fun observeTimer() {
        countdownTimerManager.onEachSecond(scope) { timerText ->
            _timerState.update { timerText }
        }
    }

    private fun observeTimerEvents() {
        countdownTimerManager.events
            .onEach { event ->
                if (event is CountdownTimerManager.TimerEvent.Finished) {
                    if (_recallState.value.isFinished) return@onEach
                    _recallState.update { state -> state.copy(isFinished = true) }

                    val numbersAnswer = NumbersAnswer(values = _recallState.value.answers)

                    _actions.send(RecallAction.OnTimeUp(numbersAnswer))
                }
            }.launchIn(scope)
    }

    fun onIntent(intent: RecallIntent) {
        when (intent) {
            is RecallIntent.OnUserAnswerChanged -> changeUserAnswer(
                index = intent.index,
                answer = intent.answer
            )

            RecallIntent.OnCompleteClick -> finishRecall()
        }
    }

    private fun changeUserAnswer(index: Int, answer: String) {
        val answerOrNull = answer.ifEmpty { null }

        _recallState.update { state ->
            val newAnswers = state.answers.toMutableList().apply {
                set(index = index, answerOrNull?.toInt())
            }

            state.copy(
                answers = newAnswers,
                isAllFilled = newAnswers.none { it == null }
            )
        }
    }

    private fun finishRecall() {
        if (_recallState.value.isFinished) return
        _recallState.update { state -> state.copy(isFinished = true) }

        val numbersAnswer = NumbersAnswer(values = _recallState.value.answers)

        scope.launch {
            _actions.send(RecallAction.FinishRecall(numbersAnswer))
        }
    }

    override fun onDestroy() {
        countdownTimerManager.stop()
        scope.cancel()
    }
}

data class RecallUiState(
    val answers: List<Int?> = emptyList(),
    val isAllFilled: Boolean = false,
    val itemPerPage: Int = 9,
    val isFinished: Boolean = false
) {
    val pageCount: Int
        get() = ceil(answers.size.toDouble() / itemPerPage).toInt()
}

sealed interface RecallIntent {
    data class OnUserAnswerChanged(val index: Int, val answer: String) : RecallIntent
    data object OnCompleteClick : RecallIntent
}

sealed interface RecallAction {
    data class OnTimeUp(val answers: NumbersAnswer) : RecallAction
    data class FinishRecall(val answers: NumbersAnswer) : RecallAction
}