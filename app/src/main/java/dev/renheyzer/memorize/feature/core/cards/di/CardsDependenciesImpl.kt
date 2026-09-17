package dev.renheyzer.memorize.feature.core.cards.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.feature.core.cards.CardsRepositoryImpl
import dev.renheyzer.memorize.feature.core.cards.data.source.remote.CardsRemoteDataSourceImpl
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.CalculateCardsResultUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.ShuffleDeckUseCase

class CardsDependenciesImpl(
    dispatchers: AppDispatchers,
    firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) : CardsDependencies {
    override val cardsRemoteDataSource by lazy(LazyThreadSafetyMode.NONE) {
        CardsRemoteDataSourceImpl(
            ioDispatcher = dispatchers.io,
            firebaseAuth = firebaseAuth,
            firestore = firestore
        )
    }
    override val cardsRepository by lazy(LazyThreadSafetyMode.NONE) {
        CardsRepositoryImpl(
            remoteDataSource = cardsRemoteDataSource
        )
    }
    override val generateOrderedDeckUseCase by lazy(LazyThreadSafetyMode.NONE) { GenerateOrderedDeckUseCase() }
    override val shuffleDeckUseCase by lazy(LazyThreadSafetyMode.NONE) { ShuffleDeckUseCase() }
    override val calculateCardsResultUseCase by lazy(LazyThreadSafetyMode.NONE) { CalculateCardsResultUseCase() }
}