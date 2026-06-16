package dev.renheyzer.memorize.feature.core.cards.di

import dev.renheyzer.memorize.feature.core.cards.domain.usecase.CalculateCardsResultUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.ShuffleDeckUseCase

class CardsDependenciesImpl : CardsDependencies {
    override val generateOrderedDeckUseCase by lazy(LazyThreadSafetyMode.NONE) { GenerateOrderedDeckUseCase() }
    override val shuffleDeckUseCase by lazy(LazyThreadSafetyMode.NONE) { ShuffleDeckUseCase() }
    override val calculateCardsResultUseCase by lazy(LazyThreadSafetyMode.NONE) { CalculateCardsResultUseCase() }
}