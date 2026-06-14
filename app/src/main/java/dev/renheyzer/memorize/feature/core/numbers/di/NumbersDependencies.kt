package dev.renheyzer.memorize.feature.core.numbers.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CalculateNumbersResultUseCase
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase

interface NumbersDependencies : InstanceKeeper.Instance {

    val numbersRepository: NumbersRepository

    val generateNumbersUseCase: GenerateNumbersUseCase
    val calculateNumbersResultUseCase: CalculateNumbersResultUseCase
}