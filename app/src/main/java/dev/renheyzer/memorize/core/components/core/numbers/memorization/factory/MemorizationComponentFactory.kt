package dev.renheyzer.memorize.core.components.core.numbers.memorization.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependencies
import dev.renheyzer.memorize.core.components.core.numbers.memorization.MemorizationComponent
import dev.renheyzer.memorize.core.components.core.numbers.memorization.DefaultMemorizationComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createMemorizationComponent(
    context: ComponentContext,
    numbersDependencies: NumbersDependencies,
    params: MemorizationComponent.Params,
    navigateToRecall: () -> Unit
): MemorizationComponent =
    DefaultMemorizationComponent(
        componentContext = context,
        env = appDependencies.componentEnvironment,
        generateNumbersUseCase = numbersDependencies.generateNumbersUseCase,
        gameSessionStore = numbersDependencies.gameSessionStore,
        countdownTimerManager = appDependencies.countdownTimerManager,
        params = params,
        navigateToRecall = navigateToRecall
    )