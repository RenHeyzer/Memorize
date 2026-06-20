package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card

@Composable
fun CardsPager(
    pagerState: PagerState,
    cardPages: List<List<Card>>,
    columnsCount: Int,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
    ) { pageIndex ->
        val cardsForThisPage = cardPages[pageIndex]

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = columnsCount),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .widthIn(min = 300.dp, max = 560.dp),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
        ) {
            items(items = cardsForThisPage, key = { it.id }) { card ->
                CardItem(card)
            }
        }
    }
}