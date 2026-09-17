package dev.renheyzer.memorize.feature.core.cards.presentation.component.setup

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam

fun ComponentFactory.createCardsSetupComponent(
    context: ComponentContext,
    onStartGameRequested: (params: CardsParam) -> Unit
): CardsSetupComponent = DefaultCardsSetupComponent(
    componentContext = context,
    env = appDependencies.componentEnvironment,
    onStartGameRequested = onStartGameRequested
)