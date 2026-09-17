package dev.renheyzer.memorize.feature.core.cards.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.cards.CardsRepository
import dev.renheyzer.memorize.feature.core.cards.data.source.remote.CardsRemoteDataSource
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.CalculateCardsResultUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.ShuffleDeckUseCase

interface CardsDependencies : InstanceKeeper.Instance {
    val cardsRemoteDataSource: CardsRemoteDataSource
    val cardsRepository: CardsRepository
    val generateOrderedDeckUseCase: GenerateOrderedDeckUseCase
    val shuffleDeckUseCase: ShuffleDeckUseCase
    val calculateCardsResultUseCase: CalculateCardsResultUseCase
}