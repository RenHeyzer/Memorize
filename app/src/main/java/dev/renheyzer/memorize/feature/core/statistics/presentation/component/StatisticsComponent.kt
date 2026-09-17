package dev.renheyzer.memorize.feature.core.statistics.presentation.component

import dev.renheyzer.memorize.feature.core.statistics.presentation.store.StatisticsUiState
import kotlinx.coroutines.flow.StateFlow

interface StatisticsComponent {

    val uiState: StateFlow<StatisticsUiState>
}