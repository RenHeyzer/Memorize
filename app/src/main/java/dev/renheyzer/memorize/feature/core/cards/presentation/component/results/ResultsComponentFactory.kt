package dev.renheyzer.memorize.feature.core.cards.presentation.component.results

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult

fun ComponentFactory.createResultsComponent(
    context: ComponentContext,
    results: CardsResult,
    onResultsCompleted: () -> Unit,
    onPlayAgainRequested: () -> Unit,
): ResultsComponent = DefaultResultsComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    results = results,
    onResultsCompleted = onResultsCompleted,
    onPlayAgainRequested = onPlayAgainRequested
)