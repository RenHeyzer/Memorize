package dev.renheyzer.memorize.feature.core.numbers.presentation.store.memorization

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.onEachSecond
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersMode
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
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

class MemorizationStore(
    mainContext: CoroutineContext,
    private val countdownTimerManager: CountdownTimerManager,
    params: NumbersParam,
    task: NumbersTask
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(
        MemorizationUiState(
            numbers = task.numbers,
            quantity = params.quantity,
            mode = params.mode
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _timerState = MutableStateFlow("")
    val timerState = _timerState.asStateFlow()

    private val _actions = Channel<MemorizationAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        countdownTimerManager.setDuration(params.memorizationTimeSeconds * 1000L)

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
            .onEach { value ->
                if (value is CountdownTimerManager.TimerEvent.Finished) {
                    if (_uiState.value.isFinished) return@onEach
                    _uiState.update { state -> state.copy(isFinished = true) }

                    _actions.send(MemorizationAction.OnTimeUp)
                }
            }.launchIn(scope)
    }

    fun finishMemorization() {
        if (_uiState.value.isFinished) return
        _uiState.update { state -> state.copy(isFinished = true) }

        scope.launch {
            _actions.send(MemorizationAction.FinishMemorization)
        }
    }

    override fun onDestroy() {
        countdownTimerManager.stop()
        scope.cancel()
    }
}

data class MemorizationUiState(
    val numbers: List<Int> = emptyList(),
    val quantity: Int = 0,
    val mode: NumbersMode = NumbersMode.RANDOM,
    val itemPerPage: Int = 9,
    val isFinished: Boolean = false
) {
    val pageCount: Int
        get() = ceil(numbers.size.toDouble() / itemPerPage).toInt()
}

sealed interface MemorizationAction {
    data object OnTimeUp : MemorizationAction
    data object FinishMemorization : MemorizationAction
}