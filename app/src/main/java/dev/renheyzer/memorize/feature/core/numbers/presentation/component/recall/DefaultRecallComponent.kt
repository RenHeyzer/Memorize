package dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnResume
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallEvents
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallStore
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallUiState
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultRecallComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val gameSessionStore: GameSessionStore,
    private val countdownTimerManager: CountdownTimerManager,
    private val time: Long,
    private val navigateToResults: () -> Unit
) : RecallComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        RecallStore(
            env = env,
            gameSessionStore = gameSessionStore,
            countdownTimerManager = countdownTimerManager,
            time = time
        )
    }

    override val uiState: StateFlow<RecallUiState> = store.recallState

    override val timerState: StateFlow<String> = store.timerState

    init {
        observeEvents()

        lifecycle.doOnResume {
            store.startTimer()
        }
    }

    private fun observeEvents() {
        scope.launch {
            store.events.collect { event ->
                when (event) {
                    is RecallEvents.OnTimeOut -> {
                        val message = env.stringResolver.resolve(event.message)
                        store.showMessage(message = message)
                        delay(2000L)
                        navigateToResults()
                    }

                    RecallEvents.NavigateToResults -> {
                        navigateToResults()
                    }
                }
            }
        }
    }

    override fun whenUserEnteredAnswer(index: Int, answer: String) {
        store.addUserAnswer(index = index, answer = answer)
    }

    override fun onCompleteClick() {
        store.saveUserAnswers()
    }
}