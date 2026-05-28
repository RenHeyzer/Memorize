package dev.renheyzer.memorize.feature.auth.domain.usecase.registration

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.feature.auth.data.repositories.AuthRepository
import dev.renheyzer.memorize.feature.auth.domain.model.User

class RegisterByEmailUseCase(
    private val compositeValidator: CompositeValidator,
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Either<AppError, User> {
        val validationResult = compositeValidator(email, password, confirmPassword)
        return if (validationResult.hasError()) {
            Either.Left(validationResult)
        } else {
            repository.registerByEmail(email, password)
        }
    }
}