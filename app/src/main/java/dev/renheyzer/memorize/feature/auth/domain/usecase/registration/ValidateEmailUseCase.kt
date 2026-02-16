package dev.renheyzer.memorize.feature.auth.domain.usecase.registration

class ValidateEmailUseCase {

    operator fun invoke(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return !regex.matches(email)
    }
}