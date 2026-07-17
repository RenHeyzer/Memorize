package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CorrectOrderDeck(
    selectedCard: Card?,
    onCardSelected: (source: Card?) -> Unit,
    orderedDeck: List<Card>,
    answersDeck: List<Card?>,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(64.dp),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = orderedDeck,
            key = { card -> card.id }
        ) { card ->
            val isAlreadyRecalled = answersDeck.any { it?.id == card.id }
            val isSelected = card.id == selectedCard?.id

            AnimatedContent(
                targetState = isAlreadyRecalled,
                modifier = Modifier.animateItem(),
                label = "SelectedCardTransition"
            ) { cardState ->
                if (!cardState) {
                    with(sharedTransitionScope) {
                        SourceCard(
                            card = card,
                            isSelected = isSelected,
                            onClick = { onCardSelected(if (isSelected) null else card) },
                            modifier = Modifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "card_${card.id}"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        )
                    }
                } else {
                    Box(modifier = Modifier.aspectRatio(0.72f))
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@ThemePreviews
@AdaptiveDevicePreviews
@Composable
private fun PreviewCorrectOrderDeck() {
    MemorizeTheme {
        var selectedCard by remember {
            mutableStateOf<Card?>(
                Card(
                    rank = CardRank.ACE,
                    suit = CardSuit.CLUBS
                )
            )
        }
        val orderedDeck by remember {
            mutableStateOf  (
                GenerateOrderedDeckUseCase()(
                    listOf(CardSuit.CLUBS, CardSuit.HEARTS)
                )
            )
        }
        val answersDeck = remember {
            mutableStateListOf(Card(suit = CardSuit.HEARTS, rank = CardRank.NINE))
        }
        SharedTransitionScope {
            CorrectOrderDeck(
                selectedCard = selectedCard,
                onCardSelected = { selectedCard = it },
                orderedDeck = orderedDeck,
                answersDeck = answersDeck,
                sharedTransitionScope = this
            )
        }
    }
}