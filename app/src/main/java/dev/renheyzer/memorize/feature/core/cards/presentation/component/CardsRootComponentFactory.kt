package dev.renheyzer.memorize.feature.core.cards.presentation.component

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createCardsRootComponent(
    context: ComponentContext,
    backHome: () -> Unit
): CardsRootComponent = DefaultCardsRootComponent(
    componentContext = context,
    factory = this,
    cardsDependenciesFactory = { appDependencies.cardsDependencies() },
    backHome = backHome
)