package dev.renheyzer.memorize.feature.core.cards.presentation.component.results

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsAction
import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsStore
import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsUiState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultResultsComponent(
    componentContext: ComponentContext,
    env: ComponentEnvironment,
    results: CardsResult,
    private val onResultsCompleted: () -> Unit,
    private val onPlayAgainRequested: () -> Unit,
): ComponentContext by componentContext, ResultsComponent {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        ResultsStore(
            mainContext = env.mainContext,
            results = results
        )
    }

    override val uiState: StateFlow<ResultsUiState> = store.uiState

    init {
        observeActions()
    }

    private fun observeActions() {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    ResultsAction.CompleteRequested -> onResultsCompleted()
                    ResultsAction.PlayAgainRequested -> onPlayAgainRequested()
                }
            }
        }
    }

    override fun onIntent(intent: ResultsIntent) {
        store.onIntent(intent)
    }
}