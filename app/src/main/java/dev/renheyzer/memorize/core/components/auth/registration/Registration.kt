package dev.renheyzer.memorize.core.components.auth.registration

import dev.renheyzer.memorize.core.components.auth.registration.store.RegistrationUiState
import kotlinx.coroutines.flow.StateFlow

interface Registration {

    val uiState: StateFlow<RegistrationUiState>
    fun onSignUpClick(email: String, password: String, confirmPassword: String)
    fun onAlreadyHaveAnAccountClick()
}