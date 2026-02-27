package dev.renheyzer.memorize.core.components.core.numbers.dependencies

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CheckAnswersUseCase
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase

interface NumbersDependencies : InstanceKeeper.Instance {

    val numbersRepository: NumbersRepository

    val generateNumbersUseCase: GenerateNumbersUseCase
    val checkAnswersUseCase: CheckAnswersUseCase
    val gameSessionStore: GameSessionStore
}