package dev.renheyzer.memorize.core.components.root

import android.net.Uri
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.auth.AuthComponent
import dev.renheyzer.memorize.core.components.core.CoreRootComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun handleDeepLink(uri: Uri)

    sealed class Child {
        class Core(val component: CoreRootComponent) : Child()
        class Auth(val component: AuthComponent) : Child()
    }
}