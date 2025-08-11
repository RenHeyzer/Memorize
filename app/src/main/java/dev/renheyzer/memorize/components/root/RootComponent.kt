package dev.renheyzer.memorize.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.components.home.Home

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class HomeChild(val component: Home) : Child()
    }
}