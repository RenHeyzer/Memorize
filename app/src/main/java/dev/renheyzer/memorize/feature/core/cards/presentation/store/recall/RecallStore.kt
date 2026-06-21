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

class RecallStore(
    mainContext: CoroutineContext,
    private val countdownTimerManager: CountdownTimerManager,
    orderedDeck: List<Card>,
    params: CardsParam,
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(
        RecallUiState(
            orderedDeck = orderedDeck,
            answers = CardsAnswer.empty(params.quantity)
        )
    )
    val uiState = _uiState.asStateFlow()

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
                    if (_uiState.value.isFinished) return@onEach
                    _uiState.update { state -> state.copy(isFinished = true) }

                    val cardsAnswer = _uiState.value.answers

                    _actions.send(RecallAction.OnTimeUp(cardsAnswer))
                }
            }.launchIn(scope)
    }

    fun onIntent(intent: RecallIntent) {
        when (intent) {
            is RecallIntent.OnCardSelected -> handleCardSelection(intent.selectedCard)

            is RecallIntent.OnCardMoved -> handleCardMovement(
                index = intent.index,
            )

            RecallIntent.OnCheckClicked -> finishRecall()
        }
    }

    private fun handleCardSelection(selectedCard: Card?) {
        _uiState.update { state ->
            val isCardSelected = state.orderedDeck.any { it.id == selectedCard?.id }

            state.copy(isCardSelected = isCardSelected, selectedCard = selectedCard)
        }
    }

    private fun handleCardMovement(index: Int) {
        _uiState.update { state ->
            val targetSlot = state.answers.values[index]

            val newAnswers = state.answers.values.toMutableList().apply {
                if (state.selectedCard != null && targetSlot == null) {
                    set(index = index, state.selectedCard)
                } else if (targetSlot != null) {
                    set(index = index, null)
                }
            }

            state.copy(
                isCardSelected = false,
                selectedCard = null,
                answers = state.answers.copy(values = newAnswers),
                isAllFilled = newAnswers.none { it == null }
            )
        }
    }

    private fun finishRecall() {
        if (_uiState.value.isFinished) return
        _uiState.update { state -> state.copy(isFinished = true) }

        val answers = _uiState.value.answers

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
    val orderedDeck: List<Card> = emptyList(),
    val answers: CardsAnswer = CardsAnswer.empty(0),
    val isCardSelected: Boolean = false,
    val selectedCard: Card? = null,
    val isAllFilled: Boolean = false,
    val columns: Int = 3,
    val isFinished: Boolean = false
)

sealed interface RecallIntent {
    data class OnCardSelected(val selectedCard: Card?) : RecallIntent
    data class OnCardMoved(val index: Int) : RecallIntent
    data object OnCheckClicked : RecallIntent
}

sealed interface RecallAction {
    data class OnTimeUp(val answers: CardsAnswer) : RecallAction
    data class FinishRecall(val answers: CardsAnswer) : RecallAction
}
