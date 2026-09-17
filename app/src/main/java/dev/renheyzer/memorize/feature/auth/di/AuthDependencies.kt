package dev.renheyzer.memorize.feature.auth.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.models.AuthConfig
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSource
import dev.renheyzer.memorize.feature.auth.domain.repository.AuthRepository
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.CompositeValidator
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.RegisterByEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidateEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidatePasswordUseCase

interface AuthDependencies : InstanceKeeper.Instance {
    val authConfig: AuthConfig
    val authRemoteDataSource: AuthRemoteDataSource

    val authRepository: AuthRepository
    val validateEmailUseCase: ValidateEmailUseCase
    val validatePasswordUseCase: ValidatePasswordUseCase

    val compositeValidator: CompositeValidator

    val registerByEmailUseCase: RegisterByEmailUseCase
}