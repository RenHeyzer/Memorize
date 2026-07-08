package dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupAction
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupState
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupStore
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNumbersSetupComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val saveParamsAndStartGame: (params: NumbersParam) -> Unit
) : NumbersSetupComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val store = instanceKeeper.getOrCreate {
        NumbersSetupStore(mainContext = env.mainContext)
    }

    override val uiState: StateFlow<NumbersSetupState> = store.uiState

    init {
        observeActions()
    }

    private fun observeActions() {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    is NumbersSetupAction.SaveParamsAndStartGame -> {
                        saveParamsAndStartGame(action.params)
                    }
                }
            }
        }
    }

    override fun onIntent(intent: NumbersSetupIntent) {
        store.onIntent(intent = intent)
    }
}