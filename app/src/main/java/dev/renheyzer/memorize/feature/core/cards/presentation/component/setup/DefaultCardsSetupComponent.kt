package dev.renheyzer.memorize.feature.core.cards.presentation.component.setup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupAction
import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupIntent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupState
import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupStore
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCardsSetupComponent(
    componentContext: ComponentContext,
    env: ComponentEnvironment,
    private val onStartGameRequested: (params: CardsParam) -> Unit
): ComponentContext by componentContext, CardsSetupComponent {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        CardsSetupStore(
            mainContext = env.mainContext
        )
    }

    override val uiState: StateFlow<CardsSetupState> = store.uiState

    init {
        observeActions()
    }

    private fun observeActions() {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    is CardsSetupAction.SaveParamsAndStartGame -> onStartGameRequested(action.params)
                }
            }
        }
    }

    override fun onIntent(intent: CardsSetupIntent) {
        store.onIntent(intent)
    }
}