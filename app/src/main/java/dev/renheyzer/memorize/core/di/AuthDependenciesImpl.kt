package dev.renheyzer.memorize.core.di

import com.google.firebase.auth.FirebaseAuth
import dev.renheyzer.memorize.BuildConfig
import dev.renheyzer.memorize.core.models.AuthConfig
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSource
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSourceImpl
import dev.renheyzer.memorize.feature.auth.data.repositories.AuthRepository
import dev.renheyzer.memorize.feature.auth.data.repositories.AuthRepositoryImpl
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.CompositeValidator
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.RegisterByEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidateEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidatePasswordUseCase

class AuthDependenciesImpl(private val firebaseAuth: FirebaseAuth) : AuthDependencies {

    override val authConfig: AuthConfig = AuthConfig(deepLinkUrl = BuildConfig.DEEP_LINK_URL)
    override val authRemoteDataSource: AuthRemoteDataSource = AuthRemoteDataSourceImpl(firebaseAuth)

    override val authRepository: AuthRepository = AuthRepositoryImpl(authRemoteDataSource, authConfig)
    override val validateEmailUseCase = ValidateEmailUseCase()
    override val validatePasswordUseCase = ValidatePasswordUseCase()
    override val compositeValidator = CompositeValidator(
        validateEmail = validateEmailUseCase,
        validatePassword = validatePasswordUseCase,
    )

    override val registerByEmailUseCase = RegisterByEmailUseCase(
        compositeValidator = compositeValidator,
        repository = authRepository
    )
}