package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CheckAnswersUseCase
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsStore
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultsUiState
import kotlinx.coroutines.flow.StateFlow

class DefaultResultsComponent(
    componentContext: ComponentContext,
    private val gameSessionStore: GameSessionStore,
    private val checkAnswersUseCase: CheckAnswersUseCase,
    private val navigateToHome: () -> Unit,
    private val navigateToSetup: () -> Unit,
) : ResultsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getOrCreate {
        ResultsStore(
            gameSessionStore = gameSessionStore,
            checkAnswersUseCase = checkAnswersUseCase
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