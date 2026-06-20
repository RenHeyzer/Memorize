package dev.renheyzer.memorize.feature.core.cards.presentation.store.recall

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.onEachSecond
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
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
    params: CardsParam,
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _recallState = MutableStateFlow(
        RecallUiState(
            answers = CardsAnswer.empty(params.quantity)
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

                    val cardsAnswer = _recallState.value.answers

                    _actions.send(RecallAction.OnTimeUp(cardsAnswer))
                }
            }.launchIn(scope)
    }

    fun onIntent(intent: RecallIntent) {
        when (intent) {
            is RecallIntent.OnUserAnswerChanged -> changeUserAnswer(
                index = intent.index,
                card = intent.card
            )

            RecallIntent.OnCompleteClick -> finishRecall()
        }
    }

    private fun changeUserAnswer(index: Int, card: Card?) {
        _recallState.update { state ->
            val newValues = state.answers.values.toMutableList().apply {
                set(index = index, card)
            }

            state.copy(
                answers = state.answers.copy(values = newValues),
                isAllFilled = newValues.none { it == null }
            )
        }
    }

    private fun finishRecall() {
        if (_recallState.value.isFinished) return
        _recallState.update { state -> state.copy(isFinished = true) }

        val answers = _recallState.value.answers

        scope.launch {
            _actions.send(RecallAction.FinishRecall(answers))
        }
    }

    override fun onDestroy() {
        countdownTimerManager.stop()
        scope.cancel()
    }
}

data class RecallUiState(
    val answers: CardsAnswer = CardsAnswer.empty(0),
    val isAllFilled: Boolean = false,
    val itemPerPage: Int = 3,
    val isFinished: Boolean = false
) {
    val pageCount: Int
        get() = ceil(answers.values.size.toDouble() / itemPerPage).toInt()
}

sealed interface RecallIntent {
    data class OnUserAnswerChanged(val index: Int, val card: Card?) : RecallIntent
    data object OnCompleteClick : RecallIntent
}

sealed interface RecallAction {
    data class OnTimeUp(val answers: CardsAnswer) : RecallAction
    data class FinishRecall(val answers: CardsAnswer) : RecallAction
}
