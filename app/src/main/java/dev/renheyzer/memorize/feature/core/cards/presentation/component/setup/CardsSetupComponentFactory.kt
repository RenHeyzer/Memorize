package dev.renheyzer.memorize.feature.core.cards.presentation.component.setup

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createCardsSetupComponent(
    context: ComponentContext
): CardsSetupComponent = DefaultCardsSetupComponent(
    componentContext = context
)