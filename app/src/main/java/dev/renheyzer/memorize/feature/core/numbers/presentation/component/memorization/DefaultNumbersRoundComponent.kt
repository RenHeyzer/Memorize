package dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import dev.renheyzer.memorize.feature.core.numbers.domain.model.RoundResult
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CalculateRoundResultUseCase
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundAction
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.RoundStore
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNumbersRoundComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val countdownTimerManager: CountdownTimerManager,
    private val args: NumbersRoundComponentArgs
) : NumbersRoundComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        RoundStore(
            mainContext = env.mainContext,
            countdownTimerManager = countdownTimerManager,
            params = args.params,
            currentRound = args.currentRound,
            task = args.task,
            calculateRoundResults = args.calculateRoundResultsUseCase
        )
    }

    override val uiState: StateFlow<NumbersRoundState> = store.uiState

    override val timerState: StateFlow<String> = store.timerState

    init {
        observeActions()

        lifecycle.doOnResume {
            store.startTimer()
        }

        lifecycle.doOnPause {
            store.pauseTimer()
        }
    }

    private fun observeActions() {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    is NumbersRoundAction.FinishRound -> args.navigateToResult(action.roundResult)
                    NumbersRoundAction.NavigateHome -> args.navigateHome()
                }
            }
        }
    }

    override fun onIntent(intent: NumbersRoundIntent) {
        store.onIntent(intent)
    }
}

data class NumbersRoundComponentArgs(
    val params: NumbersParam,
    val currentRound: Int,
    val task: NumbersTask,
    val calculateRoundResultsUseCase: CalculateRoundResultUseCase,
    val navigateToResult: (roundResult: RoundResult) -> Unit,
    val navigateHome: () -> Unit
)