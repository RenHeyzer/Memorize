package dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersMode
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.presenatation.utils.parseTimeDigitsToSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NumbersSetupStore(
    mainContext: CoroutineContext
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(NumbersSetupState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<NumbersSetupAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun onIntent(intent: NumbersSetupIntent) {
        when (intent) {
            is NumbersSetupIntent.OnQuantityChanged -> validateQuantity(intent.quantityInput)
            is NumbersSetupIntent.OnRememberTimeChanged -> validateRememberTime(intent.rememberTimeInput)

            is NumbersSetupIntent.OnBinaryToggled -> {
                _uiState.update { state -> state.copy(isBinary = intent.isBinary) }
            }

            NumbersSetupIntent.OnStartClicked -> startGameIfValid()
        }
    }

    private fun validateQuantity(input: String) {
        if (input.isBlank()) {
            _uiState.update { state ->
                state.copy(parsedQuantity = null, quantityError = null)
            }
            return
        }

        val quantity = input.toIntOrNull()
        val isValid = quantity in 1..100
        val error =
            if (!isValid) UiText.StringResource(R.string.numbers_setup_quantity_error_message) else null

        _uiState.update { state ->
            state.copy(
                parsedQuantity = if (isValid) quantity else null,
                quantityError = error
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
        val state = _uiState.value

        val quantity = state.parsedQuantity ?: return
        val memorizationTimeSeconds = state.parsedRememberTimeSeconds ?: return
        val mode = if (state.isBinary) NumbersMode.BINARY else NumbersMode.RANDOM

        val params = NumbersParam(
            quantity = quantity,
            mode = mode,
            memorizationTimeSeconds = memorizationTimeSeconds,
            recallTimeSeconds = memorizationTimeSeconds * 2
        )

        scope.launch {
            _actions.send(NumbersSetupAction.SaveParamsAndStartGame(params))
        }
    }
}

data class NumbersSetupState(
    val parsedQuantity: Int? = null,
    val parsedRememberTimeSeconds: Int? = null,
    val isBinary: Boolean = false,
    val quantityError: UiText? = null,
    val rememberTimeError: UiText? = null
) {
    val isStartButtonEnabled: Boolean
        get() = parsedQuantity != null && parsedRememberTimeSeconds != null
}

sealed interface NumbersSetupIntent {
    data class OnQuantityChanged(val quantityInput: String) : NumbersSetupIntent
    data class OnRememberTimeChanged(val rememberTimeInput: String) : NumbersSetupIntent
    data class OnBinaryToggled(val isBinary: Boolean) : NumbersSetupIntent
    data object OnStartClicked : NumbersSetupIntent
}

sealed interface NumbersSetupAction {
    data class SaveParamsAndStartGame(val params: NumbersParam) : NumbersSetupAction
}