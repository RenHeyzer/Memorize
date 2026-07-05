package dev.renheyzer.memorize.feature.core.cards.presentation.store.setup

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.presenatation.utils.parseTimeDigitsToSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CardsSetupStore(
    mainContext: CoroutineContext
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(CardsSetupState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<CardsSetupAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun onIntent(intent: CardsSetupIntent) {
        when (intent) {
            is CardsSetupIntent.OnSuitToggled -> toggleSuit(intent.suit)
            is CardsSetupIntent.OnRememberTimeChanged -> validateRememberTime(intent.rememberTimeInput)
            CardsSetupIntent.OnStartClicked -> startGameIfValid()
        }
    }

    private fun toggleSuit(suit: CardSuit) {
        _uiState.update { state ->
            val newSuits = if (state.selectedSuits.contains(suit)) {
                state.selectedSuits - suit
            } else {
                state.selectedSuits + suit
            }

            val error = if (newSuits.isEmpty()) {
                UiText.StringResource(R.string.cards_setup_suits_error_message)
            } else null

            state.copy(
                selectedSuits = newSuits,
                suitsError = error
            )
        }
    }

    private fun validateRememberTime(input: String) {
        if (input.isBlank()) {
            _uiState.update { state ->
                state.copy(parsedRememberTimeSeconds = null, rememberTimeError = null)
            }
            return
        }

        val seconds = parseTimeDigitsToSeconds(input)
        val error =
            if (seconds == null) UiText.StringResource(R.string.numbers_setup_remember_time_error_message) else null

        _uiState.update { state ->
            state.copy(
                parsedRememberTimeSeconds = seconds,
                rememberTimeError = error
            )
        }
    }

    private fun startGameIfValid() {
        if (_uiState.value.isFinished) return
        _uiState.update { state -> state.copy(isFinished = true) }

        val state = _uiState.value

        if (state.selectedSuits.isEmpty()) return
        val memorizationTimeSeconds = state.parsedRememberTimeSeconds ?: return

        val params = CardsParam(
            suits = state.selectedSuits,
            memorizationTimeSeconds = memorizationTimeSeconds,
            recallTimeSeconds = memorizationTimeSeconds * 2
        )

        scope.launch {
            _actions.send(CardsSetupAction.SaveParamsAndStartGame(params))
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}

data class CardsSetupState(
    val selectedSuits: List<CardSuit> = emptyList(),
    val parsedRememberTimeSeconds: Int? = null,
    val suitsError: UiText? = null,
    val rememberTimeError: UiText? = null,
    val isFinished: Boolean = false
) {
    val isStartButtonEnabled: Boolean
        get() = selectedSuits.isNotEmpty() && parsedRememberTimeSeconds != null
}

sealed interface CardsSetupIntent {
    data class OnSuitToggled(val suit: CardSuit) : CardsSetupIntent
    data class OnRememberTimeChanged(val rememberTimeInput: String) : CardsSetupIntent
    data object OnStartClicked : CardsSetupIntent
}

sealed interface CardsSetupAction {
    data class SaveParamsAndStartGame(val params: CardsParam) : CardsSetupAction
}
