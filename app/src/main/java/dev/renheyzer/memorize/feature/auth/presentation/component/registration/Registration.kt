package dev.renheyzer.memorize.feature.auth.presentation.component.registration

import dev.renheyzer.memorize.feature.auth.presentation.store.registration.RegistrationUiState
import kotlinx.coroutines.flow.StateFlow

interface Registration {
    val uiState: StateFlow<RegistrationUiState>
    fun onSignUpClick(email: String, password: String, confirmPassword: String)
    fun onAlreadyHaveAnAccountClick()
}