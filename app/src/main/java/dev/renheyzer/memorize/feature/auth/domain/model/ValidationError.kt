package dev.renheyzer.memorize.feature.auth.domain.model

import dev.renheyzer.memorize.core.common.AppError

data class ValidationError(
    val email: Boolean = false,
    val password: PasswordError? = null,
) : AppError {
    fun hasError(): Boolean {
        return email || password != null
    }
}