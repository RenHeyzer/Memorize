package dev.renheyzer.memorize.core.components.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.auth.login.Login
import dev.renheyzer.memorize.core.components.auth.registration.Registration
import dev.renheyzer.memorize.core.components.auth.verification.Verification

interface AuthComponent {

    val stack: Value<ChildStack<*, AuthChild>>

    fun onVerificationLinkReceived(code: String)

    sealed class AuthChild {
        class RegistrationChild(val component: Registration) : AuthChild()
        class LoginChild(val component: Login) : AuthChild()
        class VerificationChild(val component: Verification) : AuthChild()
    }
}