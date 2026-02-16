package dev.renheyzer.memorize.feature.auth.data.repositories

import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.feature.auth.domain.model.User

interface AuthRepository {

    suspend fun registerByEmail(email: String, password: String): Either<AppError, User>
    suspend fun signInViaEmailAndPassword(
        email: String,
        password: String
    ): Either<NetworkError, User>

    suspend fun sendSignInLinkToEmail(email: String): Either<NetworkError, Unit>
    suspend fun signInViaEmailLink(email: String, emailLink: String): Either<NetworkError, User>
}