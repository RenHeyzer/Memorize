package dev.renheyzer.memorize.feature.auth.presentation.mapper

import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.mapper.toUiText
import dev.renheyzer.memorize.feature.auth.domain.AuthError

fun AuthError.toUiText(): UiText {
    return when (this) {
        AuthError.InvalidEmail -> UiText.StringResource(R.string.error_email_invalid)
        AuthError.InvalidCredentials -> UiText.StringResource(R.string.error_invalid_credentials)
        AuthError.UserCollision -> UiText.StringResource(R.string.error_user_collision)
        AuthError.VerificationFailed -> UiText.StringResource(R.string.error_verification_failed)
        is AuthError.Network -> cause.toUiText()
        is AuthError.Unknown -> UiText.StringResource(R.string.error_unknown)
    }
}