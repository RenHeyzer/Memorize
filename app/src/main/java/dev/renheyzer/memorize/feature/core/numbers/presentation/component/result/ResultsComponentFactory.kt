package dev.renheyzer.memorize.feature.core.numbers.presentation.component.result

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies
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