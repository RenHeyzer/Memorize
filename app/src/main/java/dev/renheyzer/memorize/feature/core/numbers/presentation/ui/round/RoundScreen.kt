package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.round

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.NumbersRoundComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.round.NumbersRoundIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.FakeRoundComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.MemoryItem
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.MemoryGrid
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.TimerContent
import dev.renheyzer.memorize.feature.core.presenatation.RoundStage
import dev.renheyzer.memorize.feature.core.presenatation.ui.dialog.ConfirmationDialog
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun RoundScreen(
    component: NumbersRoundComponent,
    modifier: Modifier = Modifier,
) {
    val timerState by component.timerState.collectAsStateWithLifecycle()
    val state by component.uiState.collectAsStateWithLifecycle()

    if (state.isShowExitDialog) {
        ConfirmationDialog(
            onDismiss = { component.onIntent(NumbersRoundIntent.ExitDismissed) },
            onConfirm = { component.onIntent(NumbersRoundIntent.ExitConfirmed) }
        )
    }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box {
                MemorizeTopBar(
                    title = stringResource(id = R.string.round_title, state.currentRound),
                    subtitle = stringResource(id = R.string.round_subtitle),
                    isBackClickEnabled = state.roundStage != RoundStage.CLEARED || state.roundStage != RoundStage.GAME_OVER,
                    onBackClick = { component.onIntent(NumbersRoundIntent.BackClicked) }
                )
                TimerContent(
                    timerValue = timerState,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        },
        bottomBar = {
            FilledTonalButton(
                onClick = { component.onIntent(NumbersRoundIntent.StageCompleted) },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                when (state.roundStage) {
                    RoundStage.MEMORIZATION -> Text("I remembered!")
                    RoundStage.RECALL -> Text("I finished!")
                    else -> {}
                }
            }
        }
    ) { innerPadding ->
        MemoryGrid(
            columns = state.columns,
            numbers = state.numbers,
            modifier = Modifier.padding(innerPadding),
            itemContent = { _, number ->
                MemoryItem(
                    number = number,
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewRoundScreen() {
    MemorizeTheme {
        RoundScreen(
            modifier = Modifier.fillMaxSize(),
            component = FakeRoundComponent(),
        )
    }
}