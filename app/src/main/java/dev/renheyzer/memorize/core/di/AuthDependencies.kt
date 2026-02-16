package dev.renheyzer.memorize.core.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.auth.data.remote.source.AuthRemoteDataSource
import dev.renheyzer.memorize.feature.auth.data.repositories.AuthRepository
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.CompositeValidator
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.RegisterByEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidateEmailUseCase
import dev.renheyzer.memorize.feature.auth.domain.usecase.registration.ValidatePasswordUseCase

interface AuthDependencies : InstanceKeeper.Instance {
    val authRemoteDataSource: AuthRemoteDataSource
    val authRepository: AuthRepository

    val validateEmailUseCase: ValidateEmailUseCase
    val validatePasswordUseCase: ValidatePasswordUseCase
    val compositeValidator: CompositeValidator

    val registerByEmailUseCase: RegisterByEmailUseCase
}