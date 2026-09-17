package dev.renheyzer.memorize.feature.auth.di

import com.google.firebase.auth.FirebaseAuth
import dev.renheyzer.memorize.BuildConfig
import dev.renheyzer.memorize.core.models.AuthConfig
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSource
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSourceImpl
import dev.renheyzer.memorize.feature.auth.data.repository.AuthRepositoryImpl
import dev.renheyzer.memorize.feature.auth.domain.repository.AuthRepository
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.CompositeValidator
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.RegisterByEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidateEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidatePasswordUseCase

class AuthDependenciesImpl(private val firebaseAuth: FirebaseAuth) : AuthDependencies {

    override val authConfig: AuthConfig by lazy(LazyThreadSafetyMode.NONE) {
        AuthConfig(deepLinkUrl = BuildConfig.VERIFICATION_DEEP_LINK_URL)
    }

    override val authRemoteDataSource: AuthRemoteDataSource by lazy(LazyThreadSafetyMode.NONE) {
        AuthRemoteDataSourceImpl(firebaseAuth)
    }

    override val authRepository: AuthRepository by lazy(LazyThreadSafetyMode.NONE) {
        AuthRepositoryImpl(authRemoteDataSource, authConfig)
    }

    override val validateEmailUseCase by lazy(LazyThreadSafetyMode.NONE) {
        ValidateEmailUseCase()
    }

    override val validatePasswordUseCase by lazy(LazyThreadSafetyMode.NONE) {
        ValidatePasswordUseCase()
    }

    override val compositeValidator by lazy(LazyThreadSafetyMode.NONE) {
        CompositeValidator(
            validateEmail = validateEmailUseCase,
            validatePassword = validatePasswordUseCase,
        )
    }

    override val registerByEmailUseCase by lazy(LazyThreadSafetyMode.NONE) {
        RegisterByEmailUseCase(
            compositeValidator = compositeValidator,
            repository = authRepository
        )
    }
}