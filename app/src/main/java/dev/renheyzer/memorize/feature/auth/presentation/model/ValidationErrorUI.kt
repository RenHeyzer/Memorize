package dev.renheyzer.memorize.feature.auth.presentation.model

import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.feature.auth.domain.model.PasswordError
import dev.renheyzer.memorize.feature.auth.domain.model.ValidationError

data class ValidationErrorUI(
    val email: UiText = UiText.Empty,
    val password: UiText = UiText.Empty,
    val confirmPassword: UiText = UiText.Empty
)

fun ValidationError.toUI(): ValidationErrorUI = ValidationErrorUI(
    email = if (this.email) UiText.StringResource(R.string.error_email_invalid) else UiText.Empty,
    password = when (this.password) {
        PasswordError.TOO_SHORT -> UiText.StringResource(R.string.error_password_too_short)
        PasswordError.NO_UPPERCASE -> UiText.StringResource(R.string.error_password_no_uppercase)
        PasswordError.NO_DIGIT -> UiText.StringResource(R.string.error_password_no_digit)
        PasswordError.NO_SPECIAL -> UiText.StringResource(R.string.error_password_no_special)
        PasswordError.MISMATCH -> UiText.StringResource(R.string.error_password_match)
        else -> UiText.Empty
    },
    confirmPassword = if (this.password == PasswordError.MISMATCH) UiText.StringResource(R.string.error_password_match) else UiText.Empty
)