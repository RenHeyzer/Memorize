package dev.renheyzer.memorize.components.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.components.auth.login.Login
import dev.renheyzer.memorize.components.auth.registration.Registration

interface AuthComponent {

    val stack: Value<ChildStack<*, AuthChild>>

    sealed class AuthChild {
        class RegistrationChild(val component: Registration) : AuthChild()
        class LoginChild(val component: Login) : AuthChild()
    }
}