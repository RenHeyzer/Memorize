package dev.renheyzer.memorize.feature.core.statistics.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.renheyzer.memorize.core.ui.SnackbarEvent
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.feature.core.statistics.di.StatisticsDependencies
import dev.renheyzer.memorize.feature.core.statistics.presentation.store.StatisticsAction
import dev.renheyzer.memorize.feature.core.statistics.presentation.store.StatisticsStore
import dev.renheyzer.memorize.feature.core.statistics.presentation.store.StatisticsUiState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultStatisticsComponent(
    componentContext: ComponentContext,
    private val env: ComponentEnvironment,
    private val statisticsDependenciesFactory: () -> StatisticsDependencies,
    private val navigateHome: () -> Unit
) : ComponentContext by componentContext, StatisticsComponent {

    private val scope = coroutineScope(env.mainContext + SupervisorJob())

    private val statisticsDependencies = instanceKeeper.getOrCreate { statisticsDependenciesFactory() }

    private val store = instanceKeeper.getOrCreate {
        StatisticsStore(
            mainContext = env.mainContext,
            statisticsRepository = statisticsDependencies.statisticsRepository
        )
    }

    override val uiState: StateFlow<StatisticsUiState> = store.uiState

    init {
        scope.launch {
            store.actions.collect { action ->
                when (action) {
                    is StatisticsAction.ShowError -> {
                        val stringMessage = env.stringResolver.resolve(action.message)

                        scope.launch {
                            env.snackbarController.sendEvent(
                                SnackbarEvent(
                                    message = stringMessage
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}