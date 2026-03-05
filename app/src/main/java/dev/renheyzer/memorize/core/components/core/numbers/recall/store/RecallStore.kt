package dev.renheyzer.memorize.core.components.core.numbers.recall.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.formatAsTimerMMSS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecallStore(
    private val env: ComponentEnvironment,
    private val gameSessionStore: GameSessionStore,
    private val countdownTimerManager: CountdownTimerManager,
    private val time: Long
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(env.mainContext + SupervisorJob())

    private val answers: MutableList<Int?> =
        MutableList(gameSessionStore.generatedNumbers.size) { null }

    private val _recallState = MutableStateFlow(
        RecallUiState(
            pagedAnswers = answers.chunked(9),
        )
    )
    val recallState = _recallState.asStateFlow()

    private val _timerState = MutableStateFlow("")
    val timerState = _timerState.asStateFlow()

    private val _events = Channel<RecallEvents>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        startTimer(time)
        observeTimer()
        observeTimerEvents()
    }

    fun startTimer(time: Long) {
        countdownTimerManager.setDuration(time)
        countdownTimerManager.start(scope)
    }

    private fun observeTimer() {
        scope.launch {
            countdownTimerManager.timeLeft.collect { value ->
                _timerState.update {
                    value.formatAsTimerMMSS()
                }
            }
        }
    }

    private fun observeTimerEvents() {
        scope.launch {
            countdownTimerManager.events.collect { event ->
                if (event is CountdownTimerManager.TimerEvent.Finished) {
                    val message = UiText.StringResource(R.string.time_up)
                    _events.send(RecallEvents.OnTimeOut(message))
                }
            }
        }
    }

    fun addUserAnswer(index: Int, answer: Int) {
        answers.add(index = index, element = answer)

        _recallState.update {

            val itemPerPage = 9
            val newPagedAnswers = answers.chunked(itemPerPage)

            val isAllFilled = answers.all { answer -> answer != null }

            it.copy(
                pagedAnswers = newPagedAnswers,
                isAllFilled = isAllFilled
            )
        }
    }

    fun saveUserAnswers() {
        val currentState = _recallState.value
        if (currentState.isAllFilled) {
            val filledUserAnswers = answers.map { it!! }

            gameSessionStore.saveUserAnswers(answers = filledUserAnswers)

            scope.launch {
                _events.send(RecallEvents.NavigateToRecall)
            }
        }
    }

    override fun onDestroy() {
        countdownTimerManager.stop()
        scope.cancel()
    }

    fun showMessage(message: String) {
        scope.launch {
            env.snackbarController.sendEvent(
                SnackbarEvent(
                    message = message
                )
            )
        }
    }
}

data class RecallUiState(
    val pagedAnswers: List<List<Int?>> = emptyList(),
    val isAllFilled: Boolean = false,
)

sealed interface RecallEvents {
    data class OnTimeOut(val message: UiText) : RecallEvents
    data object NavigateToRecall : RecallEvents
}