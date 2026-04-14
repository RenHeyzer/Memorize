package dev.renheyzer.memorize.zeature

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
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
    private val mainContext: CoroutineContext
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableStateFlow(NumbersSetupUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<NumbersSetupAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun obtainEvent(event: NumbersSetupEvent) {
        when (event) {
            is NumbersSetupEvent.OnQuantityChanged -> {
                if (event.input.isBlank()) {
                    _uiState.update { state ->
                        state.copy(
                            isStartButtonEnabled = false
                        )
                    }
                    return
                }

                val quantity = event.input.toIntOrNull() ?: 0
                if (quantity !in 1..100) {
                    _uiState.update { state ->
                        val message =
                            UiText.StringResource(R.string.numbers_setup_quantity_error_message)
                        state.copy(
                            quantityError = message,
                            isStartButtonEnabled = false
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            quantityInput = event.input,
                            quantityError = UiText.Empty,
                            isStartButtonEnabled = event.input.isNotBlank()
                                    && state.rememberTimeMin != 0 || state.rememberTimeSec != 0
                        )
                    }
                }
            }

            is NumbersSetupEvent.OnRememberTimeChanged -> {
                val rememberTimeInt = event.input.toIntOrNull() ?: 0
                val timeMin = rememberTimeInt / 100
                val timeSec = rememberTimeInt % 100

                if (timeMin !in 0..59 || timeSec !in 0..59) {
                    _uiState.update { state ->
                        val message =
                            UiText.StringResource(R.string.numbers_setup_remember_time_error_message)
                        state.copy(
                            rememberTimeError = message,
                            isStartButtonEnabled = false
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            rememberTimeMin = timeMin,
                            rememberTimeSec = timeSec,
                            rememberTimeError = UiText.Empty,
                            isStartButtonEnabled = state.quantityInput.isNotBlank()
                                    && timeMin != 0 || timeSec != 0
                        )
                    }
                }
            }

            is NumbersSetupEvent.OnBinaryToggled -> {
                _uiState.update { state ->
                    state.copy(
                        isBinary = event.isBinary,
                    )
                }
            }

            NumbersSetupEvent.OnStartClicked -> {
                val state = _uiState.value
                val quantity = state.quantityInput.toInt()
                val rememberTime =
                    (state.rememberTimeMin * 60L * 1000L) + (state.rememberTimeSec * 1000L)

                val options = NumbersSetupOptions(
                    quantity = quantity,
                    rememberTime = rememberTime,
                    recallTime = rememberTime * 2,
                    isBinary = state.isBinary
                )

                scope.launch {
                    _actions.send(NumbersSetupAction.NavigateToMemorization(options = options))
                }
            }
        }
    }
}

data class NumbersSetupUiState(
    val quantityInput: String = "",
    val rememberTimeMin: Int = 0,
    val rememberTimeSec: Int = 0,
    val isBinary: Boolean = false,
    val quantityError: UiText = UiText.Empty,
    val rememberTimeError: UiText = UiText.Empty,
    val isStartButtonEnabled: Boolean = false
)

sealed interface NumbersSetupEvent {
    data class OnQuantityChanged(val input: String) : NumbersSetupEvent
    data class OnRememberTimeChanged(val input: String) : NumbersSetupEvent
    data class OnBinaryToggled(val isBinary: Boolean) : NumbersSetupEvent
    data object OnStartClicked : NumbersSetupEvent
}

sealed interface NumbersSetupAction {
    data class NavigateToMemorization(val options: NumbersSetupOptions) : NumbersSetupAction
}