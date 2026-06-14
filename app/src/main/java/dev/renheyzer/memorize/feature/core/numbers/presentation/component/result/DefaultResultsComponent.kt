package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersResult
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsAction
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsStore
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsUiState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultResultsComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val results: NumbersResult,
    private val finishResults: () -> Unit,
    private val mapsToSetup: () -> Unit,
) : ResultsComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        ResultsStore(mainContext = env.mainContext, results = results)
    }

    override val uiState: StateFlow<ResultsUiState> = store.uiState

    init {
        observeActions()
    }

    private fun observeActions() {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    ResultsAction.FinishResults -> finishResults()
                    ResultsAction.PlayAgain -> mapsToSetup()
                }
            }
        }
    }

    override fun onIntent(intent: ResultsIntent) {
        store.onIntent(intent)
    }
}