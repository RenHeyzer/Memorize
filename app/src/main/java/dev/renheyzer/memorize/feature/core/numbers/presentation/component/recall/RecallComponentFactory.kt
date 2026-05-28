package dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies

fun ComponentFactory.createRecallComponent(
    context: ComponentContext,
    numbersDependencies: NumbersDependencies,
    time: Long,
    navigateToResults: () -> Unit
): RecallComponent = DefaultRecallComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    time = time,
    countdownTimerManager = appDependencies.countdownTimerManager,
    gameSessionStore = numbersDependencies.gameSessionStore,
    navigateToResults = navigateToResults
)