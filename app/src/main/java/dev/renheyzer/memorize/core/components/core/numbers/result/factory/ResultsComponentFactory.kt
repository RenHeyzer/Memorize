package dev.renheyzer.memorize.core.components.core.numbers.result.factory

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependencies
import dev.renheyzer.memorize.core.components.core.numbers.result.DefaultResultsComponent
import dev.renheyzer.memorize.core.components.core.numbers.result.ResultsComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createResultsComponent(
    context: ComponentContext,
    numbersDependencies: NumbersDependencies,
    navigateToHome: () -> Unit,
    navigateToSetup: () -> Unit,
): ResultsComponent = DefaultResultsComponent(
    componentContext = context,
    gameSessionStore = numbersDependencies.gameSessionStore,
    checkAnswersUseCase = numbersDependencies.checkAnswersUseCase,
    navigateToHome = navigateToHome,
    navigateToSetup = navigateToSetup
)