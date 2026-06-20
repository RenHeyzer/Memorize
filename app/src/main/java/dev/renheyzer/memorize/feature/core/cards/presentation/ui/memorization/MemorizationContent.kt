package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationUiState
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.TimerContent

@Composable
fun MemorizationContent(
    state: MemorizationUiState,
    pagerState: PagerState,
    columnsCount: Int,
    isPrevEnabled: Boolean,
    isLastPage: Boolean,
    timerValueProvider: () -> String,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimerContent(
            timerValueProvider = timerValueProvider,
            modifier = Modifier
                .padding(16.dp)
        )

        CardsPager(
            pagerState = pagerState,
            cardPages = state.cardPages,
            columnsCount = columnsCount,
        )

        NavigationControls(
            isPrevEnabled = isPrevEnabled,
            isLastPage = isLastPage,
            onPrevClick = onPrevClick,
            onNextClick = onNextClick
        )

        ViewedCards(
            columnsCount = columnsCount,
            viewedCards = state.viewedCards,
        )
    }
}