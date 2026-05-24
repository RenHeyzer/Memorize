package dev.renheyzer.memorize.feature.auth.presentation.component.verification

import dev.renheyzer.memorize.feature.auth.presentation.store.verification.VerificationUiState
import dev.renheyzer.memorize.core.ui.UiText
import kotlinx.coroutines.flow.StateFlow

interface Verification {

    val uiState: StateFlow<VerificationUiState>

    data class Params(
        val message: UiText? = null,
        val oobCode: String? = null
    )

    fun onResendClicked()
    fun onBackToLoginClicked()
    fun onNextClicked()
}