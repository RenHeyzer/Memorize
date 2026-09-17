package dev.renheyzer.memorize.feature.core.statistics.presentation.component

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createStatisticsComponent(
    context: ComponentContext,
    backHome: () -> Unit
): StatisticsComponent = DefaultStatisticsComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    statisticsDependenciesFactory = { appDependencies.statisticsDependencies() },
    navigateHome = backHome
)