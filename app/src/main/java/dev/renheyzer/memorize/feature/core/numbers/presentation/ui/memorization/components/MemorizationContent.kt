package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.core.components.core.numbers.memorization.store.MemorizationUiState
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MemorizationContent(
    timerState: StateFlow<String>,
    uiState: MemorizationUiState,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        TimerContent(
            timerState = timerState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        HorizontalPager(
            state = pagerState,
        ) { pageIndex ->

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val numbersForThisPage = uiState.numbers[pageIndex]

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.sizeIn(
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
                        NumberItem(
                            number = numbersForThisPage[index]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimerContent(
    timerState: StateFlow<String>,
    modifier: Modifier = Modifier
) {
    val timerValue by timerState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier,
        shape = MemorizeTheme.shape.card,
        color = MemorizeTheme.colors.secondaryBackground,
        shadowElevation = 8.dp,
    ) {
        Text(
            text = timerValue,
            color = MemorizeTheme.colors.primaryText,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            style = MemorizeTheme.typography.body
        )
    }
}

@Composable
fun NumberItem(number: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.aspectRatio(1f),
        shape = MemorizeTheme.shape.card,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MemorizeTheme.colors.secondaryBackground),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = number.toString(),
                color = MemorizeTheme.colors.primaryText,
                style = MemorizeTheme.typography.display
            )
        }
    }
}