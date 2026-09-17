package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.result

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.ResultComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.result.components.ResultContent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun ResultScreen(
    component: ResultComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MemorizeTopBar(
                title = stringResource(id = R.string.results_title),
                isBackClickEnabled = !state.isFinished,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        ResultContent(
            rounds = state.rounds,
            roundsResult = state.roundsResult,
            correctCount = state.correctCount,
            totalCount = state.totalCount,
            scorePercentage = state.scorePercentage,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun PreviewResultScreen() {
    MemorizeTheme {
        ResultScreen(
            component = FakeResultComponent(),
            onBackClick = {}
        )
    }
}