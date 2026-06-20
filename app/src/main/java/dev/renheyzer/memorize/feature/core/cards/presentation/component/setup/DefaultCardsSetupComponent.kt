package dev.renheyzer.memorize.feature.core.cards.presentation.component.setup

import com.arkivanov.decompose.ComponentContext

class DefaultCardsSetupComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext, CardsSetupComponent {
}