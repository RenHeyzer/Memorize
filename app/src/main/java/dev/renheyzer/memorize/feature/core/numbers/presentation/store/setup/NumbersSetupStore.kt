package dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersMode
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
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
            is NumbersSetupIntent.OnNumberOfRoundsSelected -> _uiState.update { state ->
                state.copy(
                    rounds = intent.rounds
                )
            }

            is NumbersSetupIntent.OnGirdSelected -> _uiState.update { state ->
                state.copy(
                    columns = intent.columns,
                    rows = intent.rows
                )
            }

            is NumbersSetupIntent.OnMemorizeTimeSelected -> _uiState.update { state ->
                state.copy(memorizeTime = intent.memorizeTimeInput)
            }

            is NumbersSetupIntent.OnBinaryToggled -> {
                _uiState.update { state ->
                    state.copy(isBinary = intent.isBinary)
                }
            }

            NumbersSetupIntent.OnStartClicked -> startGameIfValid()
        }
    }

    private fun startGameIfValid() {
        val state = _uiState.value

        val rounds = state.rounds ?: return
        val columns = state.columns ?: return
        val rows = state.rows ?: return
        val memorizationTimeSeconds = state.memorizeTime ?: return
        val mode = if (state.isBinary) NumbersMode.BINARY else NumbersMode.RANDOM

        val params = NumbersParam(
            rounds = rounds,
            columns = columns,
            rows = rows,
            mode = mode,
            memorizationTimeSeconds = memorizationTimeSeconds,
            recallTimeSeconds = memorizationTimeSeconds * 2
        )

        scope.launch {
            _actions.send(NumbersSetupAction.StartGame(params))
        }
    }
}

data class NumbersSetupState(
    val rounds: Int? = null,
    val columns: Int? = null,
    val rows: Int? = null,
    val memorizeTime: Int? = null,
    val isBinary: Boolean = false,
) {
    val isStartButtonEnabled: Boolean
        get() = rounds != null && columns != null && rows != null && memorizeTime != null
}

sealed interface NumbersSetupIntent {
    data class OnNumberOfRoundsSelected(val rounds: Int) : NumbersSetupIntent
    data class OnGirdSelected(val columns: Int, val rows: Int) : NumbersSetupIntent
    data class OnMemorizeTimeSelected(val memorizeTimeInput: Int) : NumbersSetupIntent
    data class OnBinaryToggled(val isBinary: Boolean) : NumbersSetupIntent
    data object OnStartClicked : NumbersSetupIntent
}

sealed interface NumbersSetupAction {
    data class StartGame(val params: NumbersParam) : NumbersSetupAction
}