package dev.renheyzer.memorize.feature.core.statistics.di

import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.feature.core.cards.data.source.remote.CardsRemoteDataSource
import dev.renheyzer.memorize.feature.core.numbers.data.source.remote.NumbersRemoteDataSource
import dev.renheyzer.memorize.feature.core.statistics.data.repository.StatisticsRepository
import dev.renheyzer.memorize.feature.core.statistics.data.repository.StatisticsRepositoryImpl

class StatisticsDependenciesImpl(
    dispatchers: AppDispatchers,
    numbersRemoteDataSource: NumbersRemoteDataSource,
    cardsRemoteDataSource: CardsRemoteDataSource
) : StatisticsDependencies {

    override val statisticsRepository: StatisticsRepository by lazy(LazyThreadSafetyMode.NONE) {
        StatisticsRepositoryImpl(
            defaultDispatcher = dispatchers.default,
            numbersRemoteDataSource = numbersRemoteDataSource,
            cardsRemoteDataSource = cardsRemoteDataSource
        )
    }
}