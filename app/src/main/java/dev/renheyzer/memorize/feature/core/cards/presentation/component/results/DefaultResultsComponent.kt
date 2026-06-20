package dev.renheyzer.memorize.feature.core.cards.presentation.component.results

import com.arkivanov.decompose.ComponentContext

class DefaultResultsComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext, ResultsComponent {
}