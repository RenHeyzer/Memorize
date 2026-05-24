package dev.renheyzer.memorize.feature.auth.data.repository

import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.actionCodeSettings
import dev.renheyzer.memorize.BuildConfig
import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.common.flatMap
import dev.renheyzer.memorize.core.common.fold
import dev.renheyzer.memorize.core.data.extension.safeApiCall
import dev.renheyzer.memorize.core.models.AuthConfig
import dev.renheyzer.memorize.feature.auth.data.mapper.toDomain
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSource
import dev.renheyzer.memorize.feature.auth.domain.AuthError
import dev.renheyzer.memorize.feature.auth.domain.model.User
import dev.renheyzer.memorize.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource,
    private val authConfig: AuthConfig
) : AuthRepository {

    private val actionCodeSettings: ActionCodeSettings
        get() = actionCodeSettings {
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            url = authConfig.deepLinkUrl
            handleCodeInApp = true
            setAndroidPackageName(
                BuildConfig.APPLICATION_ID,
                authConfig.installIfNotAvailable, // installIfNotAvailable
                authConfig.minAppSdkVersion, // minimumVersion
            )
        }

    override suspend fun registerByEmail(
        email: String,
        password: String
    ): Either<AppError, User> {
        val result = safeApiCall {
            val authResult = remote.registerByEmail(email, password)
            authResult?.user.toDomain()
        }
        return result.fold(
            onRight = { Either.Right(it) },
            onLeft = { error ->
                val authError = when (error) {
                    is NetworkError.Unknown -> {
                        val cause = error.cause
                        when (cause) {
                            is FirebaseAuthUserCollisionException -> {
                                AuthError.UserCollision
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                AuthError.InvalidCredentials
                            }

                            is FirebaseAuthEmailException -> {
                                AuthError.InvalidEmail
                            }

                            else -> {
                                error
                            }
                        }
                    }

                    else -> error
                }
                Either.Left(authError)
            }
        )
    }

    override suspend fun signInViaEmailAndPassword(
        email: String,
        password: String
    ): Either<NetworkError, User> = safeApiCall {
        val authResult = remote.signInViaEmailAndPassword(email, password)
        authResult?.user.toDomain()
    }

    override suspend fun sendSignInLinkToEmail(email: String): Either<NetworkError, Unit> {
        return safeApiCall {
            remote.sendSignInLinkToEmail(email, actionCodeSettings)
        }
    }

    override suspend fun sendEmailVerification(): Either<NetworkError, Unit> = safeApiCall {
        remote.sendEmailVerification(actionCodeSettings)
    }

    override suspend fun signInViaEmailLink(
        email: String,
        emailLink: String
    ): Either<NetworkError, User> = safeApiCall {
        remote.signInViaEmailLink(email, emailLink)?.user.toDomain()
    }

    override val isUserLoggedIn: Boolean
        get() = remote.currentUserOrNull != null

    override suspend fun onDeepLinkReceived(code: String): Either<AppError, Unit> {
        return safeApiCall {
            remote.onDeepLinkReceived(code)
            val user = remote.currentUserOrNull
            user?.reload()?.await()
            user
        }.flatMap { user ->
            if (user == null || remote.currentUser.isEmailVerified) {
                Either.Right(Unit)
            } else {
                Either.Left(AuthError.VerificationFailed)
            }
        }
    }

    override fun getCurrentUserEmail(): String? {
        return remote.currentUserOrNull?.email
    }
}