package dev.renheyzer.memorize.feature.core.numbers.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.numbers.data.source.remote.NumbersRemoteDataSource
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CalculateRoundResultUseCase
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase

interface NumbersDependencies : InstanceKeeper.Instance {

    val numbersRemoteDataSource: NumbersRemoteDataSource
    val numbersRepository: NumbersRepository

    val generateNumbersUseCase: GenerateNumbersUseCase
    val calculateRoundResultsUseCase: CalculateRoundResultUseCase
}