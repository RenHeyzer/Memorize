package dev.renheyzer.memorize.core.components.core

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.core.home.HomeComponent
import dev.renheyzer.memorize.core.components.core.numbers.NumbersRootComponent
import dev.renheyzer.memorize.core.components.core.pictures.PicturesRootComponent

interface CoreRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent): Child()
        class Pictures(val component: PicturesRootComponent): Child()
        class Numbers(val component: NumbersRootComponent): Child()
    }

    fun onBackPressed()
}