package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MemorizationContent(
    timerValueProvider: () -> String,
    numbers: List<Int?>,
    itemPerPage: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    itemContent: @Composable LazyGridItemScope.(absoluteIndex: Int, number: Int?) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TimerContent(
            timerValueProvider = timerValueProvider,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pageIndex ->
            val startIndex = pageIndex * itemPerPage
            val endIndex = minOf(startIndex + itemPerPage, numbers.size)
            val numbersForThisPage = numbers.subList(startIndex, endIndex)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .sizeIn(
                        minWidth = 300.dp,
                        minHeight = 300.dp,
                        maxWidth = 400.dp,
                        maxHeight = 400.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                ),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(numbersForThisPage.size) { index ->
                    val absoluteIndex = pageIndex * itemPerPage + index
                    itemContent(absoluteIndex, numbersForThisPage[index])
                }
            }
        }
    }
}