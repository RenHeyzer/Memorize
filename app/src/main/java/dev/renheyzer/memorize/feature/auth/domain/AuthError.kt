package dev.renheyzer.memorize.feature.auth.domain

import dev.renheyzer.memorize.core.common.AppError

sealed interface AuthError : AppError {
    data object InvalidEmail : AuthError
    data object UserCollision: AuthError
    data object InvalidCredentials: AuthError
}