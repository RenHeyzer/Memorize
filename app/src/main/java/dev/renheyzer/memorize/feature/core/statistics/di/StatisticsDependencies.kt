package dev.renheyzer.memorize.feature.core.statistics.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.renheyzer.memorize.feature.core.statistics.data.repository.StatisticsRepository

interface StatisticsDependencies : InstanceKeeper.Instance {
    val statisticsRepository: StatisticsRepository
}