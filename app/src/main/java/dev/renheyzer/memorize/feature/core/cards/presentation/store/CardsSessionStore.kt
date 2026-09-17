package dev.renheyzer.memorize.feature.core.cards.presentation.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.common.fold
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.mapper.toUiText
import dev.renheyzer.memorize.feature.core.cards.CardsRepository
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.CalculateCardsResultUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.ShuffleDeckUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CardsSessionStore(
    mainContext: CoroutineContext,
    savedState: CardsSessionState? = null,
    private val generateOrderedDeckUseCase: GenerateOrderedDeckUseCase,
    private val shuffleDeckUseCase: ShuffleDeckUseCase,
    private val calculateCardsResultUseCase: CalculateCardsResultUseCase,
    private val repository: CardsRepository
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _sessionState = MutableStateFlow(savedState ?: CardsSessionState())
    val sessionState = _sessionState.asStateFlow()

    private val _actions = Channel<CardsSessionAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun onIntent(intent: CardsSessionIntent) {
        when (intent) {
            is CardsSessionIntent.OnSetupCompleted -> onSetupCompleted(intent.params)
            CardsSessionIntent.OnMemorizationFinished -> onMemorizationFinished()
            is CardsSessionIntent.OnRecallFinished -> onRecallFinished(intent.answer)
        }
    }

    private fun onSetupCompleted(params: CardsParam) {
        val orderedDeck = generateOrderedDeckUseCase(params.suits)
        val task = shuffleDeckUseCase(orderedDeck)
        _sessionState.update { state ->
            state.copy(
                params = params,
                orderedDeck = orderedDeck,
                task = task,
                startedAtMillis = System.currentTimeMillis()
            )
        }
    }

    private fun onMemorizationFinished() {
        _sessionState.update { state ->
            state.copy(
                recallStartedAtMillis = System.currentTimeMillis()
            )
        }
    }

    private fun onRecallFinished(answer: CardsAnswer) {
        val state = _sessionState.value

        val params = requireNotNull(state.params) { "Params cannot be null when finishing recall" }
        val task = requireNotNull(state.task) { "Task cannot be null when finishing recall" }
        val startedAtMillis = requireNotNull(state.startedAtMillis) {
            "startedAtMillis cannot be null when finishing recall"
        }
        val recallStartedAtMillis = requireNotNull(state.recallStartedAtMillis) {
            "recallStartedAtMillis cannot be null when finishing recall"
        }
        val completedAtMillis = System.currentTimeMillis()

        val result = calculateCardsResultUseCase(
            params,
            task,
            answer,
            startedAtMillis,
            recallStartedAtMillis,
            completedAtMillis
        )

        scope.launch {
            repository.saveGameResult(result).fold(
                onLeft = { e ->
                    _actions.send(CardsSessionAction.ShowError(e.toUiText()))
                },
                onRight = {
                    _sessionState.update { state ->
                        state.copy(
                            answer = answer,
                            result = result,
                            completedAtMillis = completedAtMillis
                        )
                    }
                    _actions.send(CardsSessionAction.NavigateToResults)
                }
            )
        }
    }
}

sealed interface CardsSessionIntent {
    data class OnSetupCompleted(val params: CardsParam) : CardsSessionIntent
    data object OnMemorizationFinished : CardsSessionIntent
    data class OnRecallFinished(val answer: CardsAnswer) : CardsSessionIntent
}

sealed interface CardsSessionAction {
    data class ShowError(val message: UiText) : CardsSessionAction
    data object NavigateToResults : CardsSessionAction
}