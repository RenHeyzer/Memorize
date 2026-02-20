package dev.renheyzer.memorize.core.components.auth.verification

import dev.renheyzer.memorize.core.components.auth.verification.store.VerificationUiState
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