package dev.renheyzer.memorize.feature.core.presenatation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.feature.core.cards.presentation.component.CardsRootComponent
import dev.renheyzer.memorize.feature.core.home.presentation.component.HomeComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent

interface CoreRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Numbers(val component: NumbersRootComponent) : Child()
        class Cards(val component: CardsRootComponent) : Child()
    }

    fun onBackPressed()
}