package dev.renheyzer.memorize.feature.auth.domain.usecase.registration

import dev.renheyzer.memorize.feature.auth.domain.model.ValidationError

class CompositeValidator(
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) {

    operator fun invoke(email: String, password: String, confirmPassword: String): ValidationError {
        val errors = ValidationError(
            email = validateEmail(email),
            password = validatePassword(password, confirmPassword),
        )
        return errors
    }
}