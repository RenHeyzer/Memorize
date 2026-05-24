package dev.renheyzer.memorize.feature.auth.presentation.component.login

import com.arkivanov.decompose.ComponentContext

class LoginComponent(
    componentContext: ComponentContext,
    private val navigateToRegistration: () -> Unit
) : Login, ComponentContext by componentContext {

    override fun onRegistrationClicked() {
        navigateToRegistration()
    }
}