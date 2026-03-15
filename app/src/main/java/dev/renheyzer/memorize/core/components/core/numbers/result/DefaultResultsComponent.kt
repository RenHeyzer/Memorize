package dev.renheyzer.memorize.core.components.core.numbers.result

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependencies
import dev.renheyzer.memorize.core.components.core.numbers.result.store.ResultsStore
import dev.renheyzer.memorize.core.components.core.numbers.result.store.ResultsUiState
import kotlinx.coroutines.flow.StateFlow

class DefaultResultsComponent(
    componentContext: ComponentContext,
    private val numbersDependencies: NumbersDependencies,
    private val navigateToHome: () -> Unit,
    private val navigateToSetup: () -> Unit,
) : ResultsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getOrCreate {
        ResultsStore(
            gameSessionStore = numbersDependencies.gameSessionStore,
            checkAnswersUseCase = numbersDependencies.checkAnswersUseCase
        )
    }

    override val uiState: StateFlow<ResultsUiState> = store.uiState

    override fun onGoHomeClick() {
        navigateToHome()
    }

    override fun onPlayAgainClick() {
        navigateToSetup()
    }
}