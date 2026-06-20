package dev.renheyzer.memorize.feature.core.cards.presentation.component.results

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createResultsComponent(
    context: ComponentContext
): ResultsComponent = DefaultResultsComponent(
    componentContext = context
)