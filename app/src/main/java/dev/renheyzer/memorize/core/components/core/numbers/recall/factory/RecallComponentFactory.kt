package dev.renheyzer.memorize.core.components.core.numbers.recall.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependencies
import dev.renheyzer.memorize.core.components.core.numbers.recall.DefaultRecallComponent
import dev.renheyzer.memorize.core.components.core.numbers.recall.RecallComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

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