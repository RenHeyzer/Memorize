package dev.renheyzer.memorize.feature.auth.domain

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.NetworkError

sealed interface AuthError : AppError {
    data object InvalidEmail : AuthError
    data object UserCollision : AuthError
    data object InvalidCredentials : AuthError
    data object VerificationFailed : AuthError

    data class Network(val cause: NetworkError): AuthError
    data class Unknown(val cause: AppError) : AuthError
}

