package dev.renheyzer.memorize.feature.core.cards.presentation.ui.results

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.cards.presentation.component.results.ResultsComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.results.ResultsIntent

@Composable
fun ResultsScreen(
    component: ResultsComponent,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MemorizeTopBar(
                title = stringResource(R.string.cards_result_title),
                isBackClickEnabled = !state.isFinished,
                onBackClick = { component.onIntent(ResultsIntent.OnCompleteClicked) }
            )
        },
        bottomBar = {
            ResultBottomBar(
                onRetryClick = { component.onIntent(ResultsIntent.OnPlayAgainClicked) },
                onHomeClick = { component.onIntent(ResultsIntent.OnCompleteClicked) }
            )
        }
    ) { innerPadding ->
        ResultsContent(
            results = state.results,
            modifier = Modifier.padding(innerPadding)
        )
    }
}