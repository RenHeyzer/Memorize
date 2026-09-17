package dev.renheyzer.memorize.feature.core.numbers.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.feature.core.numbers.data.repository.NumbersRepositoryImpl
import dev.renheyzer.memorize.feature.core.numbers.data.source.remote.NumbersRemoteDataSource
import dev.renheyzer.memorize.feature.core.numbers.data.source.remote.NumbersRemoteDataSourceImpl
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CalculateRoundResultUseCase
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase

class NumbersDependenciesImpl(
    dispatchers: AppDispatchers,
    firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) : NumbersDependencies {

    override val numbersRemoteDataSource: NumbersRemoteDataSource by lazy(LazyThreadSafetyMode.NONE) {
        NumbersRemoteDataSourceImpl(
            ioDispatcher = dispatchers.io,
            firebaseAuth = firebaseAuth,
            firestore = firestore
        )
    }

    override val numbersRepository: NumbersRepository by lazy(LazyThreadSafetyMode.NONE) {
        NumbersRepositoryImpl(
            remoteDataSource = numbersRemoteDataSource
        )
    }

    override val generateNumbersUseCase by lazy(LazyThreadSafetyMode.NONE) { GenerateNumbersUseCase() }
    override val calculateRoundResultsUseCase by lazy(LazyThreadSafetyMode.NONE) { CalculateRoundResultUseCase() }
}