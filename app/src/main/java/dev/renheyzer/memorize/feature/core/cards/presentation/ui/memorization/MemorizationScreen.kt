package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization.FakeMemorizationComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization.MemorizationComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.memorization.MemorizationIntent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemorizationScreen(
    component: MemorizationComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state by component.uiState.collectAsStateWithLifecycle()
    val timerState by component.timerState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { state.pageCount })
    val scope = rememberCoroutineScope()

    val isPrevEnabled by remember { derivedStateOf { pagerState.currentPage != 0 } }
    val isLastPage by remember { derivedStateOf { pagerState.targetPage == pagerState.pageCount - 1 } }

    val columnsCount = remember(state.itemPerPage) { calculateColumnsCount(state.itemPerPage) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect {
            if (pagerState.targetPage == pagerState.settledPage && pagerState.settledPage != 0) {
                component.onIntent(MemorizationIntent.CardsViewed(pageIndex = pagerState.currentPage - 1))
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MemorizeTopBar(
                title = stringResource(R.string.cards_title),
                subtitle = stringResource(R.string.level_subtitle),
                isBackClickEnabled = !state.isFinished,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        MemorizationContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            pagerState = pagerState,
            columnsCount = columnsCount,
            isPrevEnabled = isPrevEnabled,
            isLastPage = isLastPage,
            timerValueProvider = { timerState },
            onPrevClick = {
                scope.launch {
                    if (pagerState.currentPage != 0) {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            },
            onNextClick = {
                if (isLastPage) {
                    component.onIntent(MemorizationIntent.CompleteClicked)
                } else {
                    component.onIntent(MemorizationIntent.CardsViewed(pageIndex = pagerState.currentPage))
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
        )
    }
}

private fun calculateColumnsCount(itemPerPage: Int): Int {
    return if (itemPerPage > 4) {
        when {
            itemPerPage % 3 == 0 -> 3
            itemPerPage % 4 == 0 -> 4
            itemPerPage % 4 < itemPerPage % 3 -> 3
            else -> 4
        }
    } else {
        itemPerPage
    }
}

@Preview
@Composable
fun PreviewMemorizationScreen(modifier: Modifier = Modifier) {
    MemorizeTheme {
        MemorizationScreen(
            component = FakeMemorizationComponent(),
            modifier = modifier,
            onBackClick = {}
        )
    }
}