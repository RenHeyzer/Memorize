package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationUiState
import dev.renheyzer.memorize.feature.core.cards.presentation.store.recall.RecallUiState
import dev.renheyzer.memorize.feature.core.presenatation.ui.TimerContent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MemorizationContent(
    state: MemorizationUiState,
    pagerState: PagerState,
    lazyGridState: LazyGridState,
    columnCount: Int,
    isPrevEnabled: Boolean,
    isLastPage: Boolean,
    timerState: StateFlow<String>,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimerContent(
            timerState = timerState
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
            lazyGridState = lazyGridState,
            columnCount = columnCount,
            viewedCards = state.viewedCards
        )
    }
}

class CardMemorizationUiStateProvider : PreviewParameterProvider<MemorizationUiState> {
    override val values = sequenceOf(
        MemorizationUiState(
            deck = listOf(
                Card(suit = CardSuit.CLUBS, rank = CardRank.ACE),
                Card(suit = CardSuit.CLUBS, rank = CardRank.TWO),
                Card(suit = CardSuit.CLUBS, rank = CardRank.QUEEN),
                Card(suit = CardSuit.HEARTS, rank = CardRank.NINE),
                Card(suit = CardSuit.HEARTS, rank = CardRank.SIX),
                Card(suit = CardSuit.HEARTS, rank = CardRank.JACK),
                Card(suit = CardSuit.DIAMONDS, rank = CardRank.FIVE),
                Card(suit = CardSuit.DIAMONDS, rank = CardRank.TEN),
                Card(suit = CardSuit.DIAMONDS, rank = CardRank.KING),
                Card(suit = CardSuit.SPADES, rank = CardRank.NINE),
                Card(suit = CardSuit.SPADES, rank = CardRank.QUEEN),
                Card(suit = CardSuit.SPADES, rank = CardRank.KING),
            ),
            viewedCards = listOf(
                Card(suit = CardSuit.CLUBS, rank = CardRank.ACE),
                Card(suit = CardSuit.CLUBS, rank = CardRank.TWO),
                Card(suit = CardSuit.CLUBS, rank = CardRank.QUEEN),
                Card(suit = CardSuit.HEARTS, rank = CardRank.NINE),
                Card(suit = CardSuit.HEARTS, rank = CardRank.SIX),
                Card(suit = CardSuit.HEARTS, rank = CardRank.JACK)
            ),
            itemPerPage = 4,
            isFinished = false
        )
    )
}

@ThemePreviews
@AdaptiveDevicePreviews
@Composable
private fun PreviewMemorizeContent(
    @PreviewParameter(CardMemorizationUiStateProvider::class) state: MemorizationUiState
) {
    MemorizeTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MemorizationContent(
                state = state,
                pagerState = rememberPagerState { state.pageCount },
                timerState = MutableStateFlow("00:00"),
                lazyGridState = LazyGridState(),
                columnCount = 4,
                isPrevEnabled = true,
                isLastPage = true,
                onPrevClick = {},
                onNextClick = {}
            )
        }
    }
}