package dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.SnackbarAction
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.UiText.StringResource
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsTask
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationAction
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationStore
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationUiState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultMemorizationComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val countdownTimerManager: CountdownTimerManager,
    params: CardsParam,
    task: CardsTask,
    private val finishMemorization: () -> Unit,
): ComponentContext by componentContext, MemorizationComponent {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        MemorizationStore(
            mainContext = env.mainContext,
            countdownTimerManager = countdownTimerManager,
            params = params,
            task = task
        )
    }

    override val uiState: StateFlow<MemorizationUiState> = store.uiState

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
                    is MemorizationAction.OnTimeUp -> {
                        val message = StringResource(R.string.time_up)
                        val strMessage = env.stringResolver.resolve(message)
                        showMessage(message = strMessage)

                        scope.launch {
                            delay(2000L)
                            finishMemorization()
                        }
                    }

                    MemorizationAction.FinishMemorization -> finishMemorization()
                }
            }
        }
    }

    fun showMessage(message: String, action: (() -> Unit)? = null) {
        scope.launch {
            env.snackbarController.sendEvent(
                SnackbarEvent(
                    message = message,
                    action = action?.let { SnackbarAction(name = "OK", action = it) }
                )
            )
        }
    }

    override fun onIntent(intent: MemorizationIntent) {
        store.onIntent(intent)
    }
}