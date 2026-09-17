package dev.renheyzer.memorize.feature.core.statistics.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.feature.core.statistics.presentation.component.StatisticsComponent

@Composable
fun StatisticsScreen(
    component: StatisticsComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state = component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            Text(text = "Statistics")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(
                text = "${state.value.numbersResults}"
            )
            Text(
                text = "${state.value.cardsResults}"
            )
        }
    }
}