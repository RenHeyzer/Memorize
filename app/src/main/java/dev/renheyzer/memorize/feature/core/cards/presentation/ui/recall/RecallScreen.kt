package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.cards.presentation.component.recall.RecallComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.recall.RecallIntent

@Composable
fun RecallScreen(
    component: RecallComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MemorizeTopBar(
                title = stringResource(R.string.cards_title),
                subtitle = stringResource(R.string.cards_recall_subtitle),
                isBackClickEnabled = !state.isFinished,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            RecallBottomBar(
                onCheckClick = { component.onIntent(RecallIntent.OnCheckClicked) },
                isComplete = state.isAllFilled,
                isCheckClickEnabled = !state.isFinished,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        RecallContent(
            modifier = Modifier.padding(innerPadding),
            timerState = component.timerState,
            uiState = state,
            onCardSelected = { selectedCard ->
                component.onIntent(RecallIntent.OnCardSelected(selectedCard))
            },
            onCardMoved = { index ->
                component.onIntent(RecallIntent.OnCardMoved(index))
            }
        )
    }
}

