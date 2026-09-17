package dev.renheyzer.memorize.feature.auth.domain.usecase.registration

import dev.renheyzer.memorize.feature.auth.domain.model.PasswordError

class ValidatePasswordUseCase {

    operator fun invoke(password: String, confirmPassword: String): PasswordError? {
        return when {
            password.length < 8 -> PasswordError.TOO_SHORT
            password.none { it.isUpperCase() } -> PasswordError.NO_UPPERCASE
            password.none { it.isDigit() } -> PasswordError.NO_DIGIT
            password.none { !it.isLetterOrDigit() } -> PasswordError.NO_SPECIAL
            password != confirmPassword -> PasswordError.MISMATCH
            else -> null
        }
    }
}