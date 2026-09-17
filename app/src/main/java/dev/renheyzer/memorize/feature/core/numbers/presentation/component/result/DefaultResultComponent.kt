package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultAction
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultStore
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultResultComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val result: NumbersResult,
    private val navigateHome: () -> Unit,
    private val navigateToSetup: () -> Unit,
) : ResultComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        ResultStore(mainContext = env.mainContext, result = result)
    }

    override val uiState: StateFlow<ResultState> = store.uiState

    init {
        observeActions()
    }

    private fun observeActions() {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    ResultAction.NavigateHome -> navigateHome()
                    ResultAction.PlayAgain -> navigateToSetup()
                }
            }
        }
    }

    override fun onIntent(intent: ResultIntent) {
        store.onIntent(intent)
    }
}