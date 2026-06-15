package dev.renheyzer.memorize.feature.core.cards.presentation.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.CalculateCardsResultUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.ShuffleDeckUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CardsSessionStore(
    savedState: CardsSessionState? = null,
    private val generateOrderedDeckUseCase: GenerateOrderedDeckUseCase,
    private val shuffleDeckUseCase: ShuffleDeckUseCase,
    private val calculateCardsResultUseCase: CalculateCardsResultUseCase
) : InstanceKeeper.Instance {

    private val _sessionState = MutableStateFlow(savedState ?: CardsSessionState())
    val sessionState = _sessionState.asStateFlow()

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

        _sessionState.update { state ->
            state.copy(
                answer = answer,
                result = result,
                completedAtMillis = completedAtMillis
            )
        }
    }
}

sealed interface CardsSessionIntent {
    data class OnSetupCompleted(val params: CardsParam) : CardsSessionIntent
    data object OnMemorizationFinished : CardsSessionIntent
    data class OnRecallFinished(val answer: CardsAnswer) : CardsSessionIntent
}
