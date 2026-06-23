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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card

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
                    SourceCard(
                        card = card,
                        isSelected = isSelected,
                        onClick = { onCardSelected(if (isSelected) null else card) },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = sharedTransitionScope
                    )
                } else {
                    Box(modifier = Modifier.aspectRatio(0.72f))
                }
            }
        }
    }
}