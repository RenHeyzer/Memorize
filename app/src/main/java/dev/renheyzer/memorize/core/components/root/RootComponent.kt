package dev.renheyzer.memorize.core.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.auth.AuthComponent
import dev.renheyzer.memorize.core.components.home.HomeComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Auth(val component: AuthComponent) : Child()
    }
}