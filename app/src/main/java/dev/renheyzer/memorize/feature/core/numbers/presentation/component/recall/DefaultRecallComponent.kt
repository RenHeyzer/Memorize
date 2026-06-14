package dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersAnswer
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallAction
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallStore
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallUiState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultRecallComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val countdownTimerManager: CountdownTimerManager,
    private val params: NumbersParam,
    private val finishRecall: (answers: NumbersAnswer) -> Unit
) : RecallComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        RecallStore(
            mainContext = env.mainContext,
            countdownTimerManager = countdownTimerManager,
            params = params,
        )
    }

    override val uiState: StateFlow<RecallUiState> = store.recallState
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
                    is RecallAction.OnTimeUp -> {
                        val message = UiText.StringResource(R.string.time_up)
                        val strMessage = env.stringResolver.resolve(message)
                        showMessage(message = strMessage)

                        scope.launch {
                            delay(2000L)
                            finishRecall(action.answers)
                        }
                    }

                    is RecallAction.FinishRecall -> {
                        finishRecall(action.answers)
                    }
                }
            }
        }
    }

    override fun onIntent(intent: RecallIntent) {
        store.onIntent(intent)
    }

    fun showMessage(message: String) {
        scope.launch {
            env.snackbarController.sendEvent(
                SnackbarEvent(
                    message = message
                )
            )
        }
    }
}