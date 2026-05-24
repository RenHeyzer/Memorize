package dev.renheyzer.memorize.feature.auth.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.feature.auth.presentation.component.login.Login
import dev.renheyzer.memorize.feature.auth.presentation.component.registration.Registration
import dev.renheyzer.memorize.feature.auth.presentation.component.verification.Verification

interface AuthComponent {

    val stack: Value<ChildStack<*, AuthChild>>

    fun onVerificationLinkReceived(code: String)

    sealed class AuthChild {
        class RegistrationChild(val component: Registration) : AuthChild()
        class LoginChild(val component: Login) : AuthChild()
        class VerificationChild(val component: Verification) : AuthChild()
    }
}