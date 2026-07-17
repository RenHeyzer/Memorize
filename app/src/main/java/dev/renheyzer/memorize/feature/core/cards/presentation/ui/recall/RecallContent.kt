package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.presentation.store.recall.RecallUiState
import dev.renheyzer.memorize.feature.core.presenatation.ui.TimerContent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecallContent(
    uiState: RecallUiState,
    timerState: StateFlow<String>,
    onCardSelected: (source: Card?) -> Unit,
    onCardMoved: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TimerContent(
                timerState = timerState,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            CorrectOrderDeck(
                selectedCard = uiState.selectedCard,
                onCardSelected = onCardSelected,
                orderedDeck = uiState.orderedDeck,
                answersDeck = uiState.answers.values,
                sharedTransitionScope = this@SharedTransitionLayout,
                modifier = Modifier.weight(0.5f)
            )

            RecallDeck(
                answersDeck = uiState.answers.values,
                onCardMoved = { index -> onCardMoved(index) },
                sharedTransitionScope = this@SharedTransitionLayout,
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

class CardRecallUiStateProvider : PreviewParameterProvider<RecallUiState> {
    override val values = sequenceOf(
        RecallUiState(
            orderedDeck = GenerateOrderedDeckUseCase()(listOf(CardSuit.CLUBS, CardSuit.HEARTS)),
            answers = CardsAnswer(
                listOf(
                    Card(suit = CardSuit.HEARTS, rank = CardRank.NINE), null,
                    Card(suit = CardSuit.HEARTS, rank = CardRank.KING),
                    Card(suit = CardSuit.CLUBS, rank = CardRank.ACE),
                    Card(suit = CardSuit.CLUBS, rank = CardRank.KING),
                    null,
                    Card(suit = CardSuit.HEARTS, rank = CardRank.QUEEN),
                    null,
                    null,
                    null,
                )
            ),
            selectedCard = Card(suit = CardSuit.CLUBS, rank = CardRank.ACE),
            isAllFilled = false,
            columns = 3,
            isFinished = false
        )
    )
}

@Composable
@ThemePreviews
@AdaptiveDevicePreviews
private fun PreviewRecallContent(
    @PreviewParameter(CardRecallUiStateProvider::class) uiState: RecallUiState
) {
    MemorizeTheme {
        RecallContent(
            uiState = uiState,
            timerState = MutableStateFlow("01:03"),
            onCardSelected = {},
            onCardMoved = {}
        )
    }
}