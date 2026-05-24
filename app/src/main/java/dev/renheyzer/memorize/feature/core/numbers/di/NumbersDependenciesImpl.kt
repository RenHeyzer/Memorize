package dev.renheyzer.memorize.feature.core.numbers.di

import dev.renheyzer.memorize.core.components.core.numbers.store.GameSessionStore
import dev.renheyzer.memorize.feature.core.numbers.data.repository.FakeNumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.repository.NumbersRepository
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.CheckAnswersUseCase
import dev.renheyzer.memorize.feature.core.numbers.domain.usecase.GenerateNumbersUseCase

class NumbersDependenciesImpl : NumbersDependencies {

    override val numbersRepository: NumbersRepository by lazy(LazyThreadSafetyMode.NONE) { FakeNumbersRepository() }

    override val generateNumbersUseCase by lazy(LazyThreadSafetyMode.NONE) { GenerateNumbersUseCase() }
    override val checkAnswersUseCase by lazy(LazyThreadSafetyMode.NONE) { CheckAnswersUseCase() }
    override val gameSessionStore by lazy(LazyThreadSafetyMode.NONE) { GameSessionStore() }
}