package dev.renheyzer.memorize.feature.core.cards.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.renheyzer.memorize.feature.core.cards.presentation.store.CardsSessionStore

class DefaultCardsRootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, CardsRootComponent {

    private val sessionStore = instanceKeeper.getOrCreate {
        CardsSessionStore()
    }
}