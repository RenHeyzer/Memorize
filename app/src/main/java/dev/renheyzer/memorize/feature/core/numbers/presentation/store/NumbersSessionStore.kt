package dev.renheyzer.memorize.feature.core.numbers.presentation.store

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CalculateNumbersResultUseCase
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersSessionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

class NumbersSessionStore(
    mainContext: CoroutineContext,
    savedState: NumbersSessionState? = null,
    private val generateNumbersUseCase: GenerateNumbersUseCase,
    private val calculateNumbersResultUseCase: CalculateNumbersResultUseCase
) : InstanceKeeper.Instance {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _sessionState = MutableStateFlow(savedState ?: NumbersSessionState())
    val sessionState = _sessionState.asStateFlow()

    fun onIntent(intent: NumbersSessionIntent) {
        when (intent) {
            is NumbersSessionIntent.OnSetupCompleted -> onSetupCompleted(params = intent.params)
            NumbersSessionIntent.OnMemorizationFinished -> onMemorizationFinished()
            is NumbersSessionIntent.OnRecallFinished -> onRecallFinished(answer = intent.answers)
        }
    }

    private fun onSetupCompleted(params: NumbersParam) {
        val task = generateNumbersUseCase(params.quantity, params.mode)
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

    private fun onRecallFinished(answer: NumbersAnswer) {
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

        val result = calculateNumbersResultUseCase(
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

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}

sealed interface NumbersSessionIntent {
    data class OnSetupCompleted(val params: NumbersParam) : NumbersSessionIntent
    data object OnMemorizationFinished : NumbersSessionIntent
    data class OnRecallFinished(val answers: NumbersAnswer) : NumbersSessionIntent
}