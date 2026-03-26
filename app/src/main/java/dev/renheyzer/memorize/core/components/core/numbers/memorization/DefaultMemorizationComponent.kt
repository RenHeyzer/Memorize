package dev.renheyzer.memorize.core.components.core.numbers.memorization

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnResume
import dev.renheyzer.memorize.core.components.core.numbers.memorization.store.MemorizationEvent
import dev.renheyzer.memorize.core.components.core.numbers.memorization.store.MemorizationStore
import dev.renheyzer.memorize.core.components.core.numbers.memorization.store.MemorizationUiState
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultMemorizationComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val generateNumbersUseCase: GenerateNumbersUseCase,
    private val gameSessionStore: GameSessionStore,
    private val countdownTimerManager: CountdownTimerManager,
    private val params: MemorizationComponent.Params,
    private val navigateToRecall: () -> Unit,
) : MemorizationComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        MemorizationStore(
            env = env,
            generateNumbersUseCase = generateNumbersUseCase,
            gameSessionStore = gameSessionStore,
            countdownTimerManager = countdownTimerManager,
            params = params,
        )
    }

    override val uiState: StateFlow<MemorizationUiState> = store.uiState

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
                    is MemorizationEvent.OnTimeUp -> {
                        val message = env.stringResolver.resolve(event.message)
                        store.showMessage(message = message)
                        delay(2000L)
                        navigateToRecall()
                    }

                    MemorizationEvent.NavigateToRecall -> {
                        navigateToRecall()
                    }
                }
            }
        }
    }

    override fun onCompleteClick() {
        store.finishMemorization()
    }
}