package dev.renheyzer.memorize.core.components.auth.login

import com.arkivanov.decompose.ComponentContext

class LoginComponent(
    componentContext: ComponentContext,
    private val navigateToRegistration: () -> Unit
) : Login, ComponentContext by componentContext {

    override fun onRegistrationClicked() {
        navigateToRegistration()

    }
}