package dev.renheyzer.memorize.core.components.core.numbers.memorization.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.components.core.numbers.memorization.MemorizationComponent
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.core.ui.SnackbarAction
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.formatAsTimerMMSS
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase
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
import kotlin.math.ceil

class MemorizationStore(
    private val env: ComponentEnvironment,
    private val generateNumbersUseCase: GenerateNumbersUseCase,
    private val gameSessionStore: GameSessionStore,
    private val countdownTimerManager: CountdownTimerManager,
    private val params: MemorizationComponent.Params,
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(env.mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(
        MemorizationUiState(
            quantity = params.quantity,
            isRandom = params.isRandom
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _timerState = MutableStateFlow("")
    val timerState = _timerState.asStateFlow()

    private val _events = Channel<MemorizationEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        generateNumbers(quantity = params.quantity, isRandom = params.isRandom)
        observeTimer()
        observeTimerEvents()
    }

    fun startTimer() {
        countdownTimerManager.setDuration(params.time)
        countdownTimerManager.start(scope)
    }

    private fun observeTimer() {
        countdownTimerManager.timeLeft
            .onEach { value ->
                _timerState.update { value.formatAsTimerMMSS() }
            }.launchIn(scope)
    }

    private fun observeTimerEvents() {
        countdownTimerManager.events
            .onEach { value ->
                if (value is CountdownTimerManager.TimerEvent.Finished) {
                    val message = UiText.StringResource(R.string.time_up)
                    _events.send(MemorizationEvent.OnTimeUp(message))
                }
            }.launchIn(scope)
    }

    private fun generateNumbers(quantity: Int, isRandom: Boolean) {
        val numbers = generateNumbersUseCase(quantity = quantity, isRandom = isRandom)
        gameSessionStore.saveGeneratedNumbers(numbers, isRandom = isRandom)

        _uiState.update {
            it.copy(numbers = numbers)
        }
    }

    fun showMessage(message: String, action: (() -> Unit)? = null) {
        scope.launch {
            env.snackbarController.sendEvent(
                SnackbarEvent(
                    message = message,
                    action = action?.let { SnackbarAction(name = "OK", action = it) }
                )
            )
        }
    }

    fun finishMemorization() {
        scope.launch {
            _events.send(MemorizationEvent.NavigateToRecall)
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
    val isRandom: Boolean = true,
    val itemPerPage: Int = 9
) {
    val pageCount: Int
        get() = ceil(numbers.size.toDouble() / itemPerPage).toInt()
}

sealed interface MemorizationEvent {
    data class OnTimeUp(val message: UiText) : MemorizationEvent
    data object NavigateToRecall : MemorizationEvent
}