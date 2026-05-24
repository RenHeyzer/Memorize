package dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies
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