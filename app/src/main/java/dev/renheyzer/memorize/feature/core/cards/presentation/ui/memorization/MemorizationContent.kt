package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationUiState
import dev.renheyzer.memorize.feature.core.presenatation.ui.TimerContent

@Composable
fun MemorizationContent(
    state: MemorizationUiState,
    pagerState: PagerState,
    columnCount: Int,
    isPrevEnabled: Boolean,
    isLastPage: Boolean,
    timerValueProvider: () -> String,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimerContent(
            timerValueProvider = timerValueProvider,
        )

        CardsPager(
            pagerState = pagerState,
            cardPages = state.cardPages,
            columnsCount = columnCount,
        )

        NavigationControls(
            isPrevEnabled = isPrevEnabled,
            isLastPage = isLastPage,
            onPrevClick = onPrevClick,
            onNextClick = onNextClick
        )

        ViewedCardsDeck(
            columnCount = columnCount,
            viewedCards = state.viewedCards,
        )
    }
}