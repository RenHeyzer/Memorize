package dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.onEachSecond
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsTask
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
    params: CardsParam,
    task: CardsTask
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(
        MemorizationUiState(
            deck = task.deck,
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

        countdownTimerManager.events.onEach { value ->
            if (value is CountdownTimerManager.TimerEvent.Finished) {
                if (_uiState.value.isFinished) return@onEach
                _uiState.update { state -> state.copy(isFinished = true) }

                _actions.send(MemorizationAction.OnTimeUp)
            }
        }.launchIn(scope)
    }

    fun onIntent(intent: MemorizationIntent) {
        when (intent) {
            is MemorizationIntent.CardsViewed -> updateViewedCards(intent.pageIndex)

            MemorizationIntent.CompleteClicked -> finishMemorization()
        }
    }

    private fun updateViewedCards(pageIndex: Int) {
        val newlyViewed = _uiState.value.cardPages.getOrNull(pageIndex) ?: emptyList()
        val updatedViewedCards = (_uiState.value.viewedCards + newlyViewed).distinctBy { it.id }

        _uiState.update { state ->
            state.copy(
                viewedCards = updatedViewedCards
            )
        }
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
    val deck: List<Card> = emptyList(),
    val viewedCards: List<Card> = emptyList(),
    val itemPerPage: Int = 3,
    val isFinished: Boolean = false
) {
    val pageCount: Int
        get() = ceil(deck.size.toDouble() / itemPerPage).toInt()

    val cardPages: List<List<Card>> = deck.chunked(itemPerPage)
}

sealed interface MemorizationIntent {
    data class CardsViewed(val pageIndex: Int) : MemorizationIntent
    data object CompleteClicked : MemorizationIntent
}

sealed interface MemorizationAction {
    data object OnTimeUp : MemorizationAction
    data object FinishMemorization : MemorizationAction
}