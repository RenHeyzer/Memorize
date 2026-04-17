package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.components.core.numbers.result.ResultsComponent
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.FooterContent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results.components.ResultsContent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.launch

@Composable
fun ResultsScreen(
    component: ResultsComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { uiState.pageCount })
    val scope = rememberCoroutineScope()

    val isPrevEnabled by remember {
        derivedStateOf { pagerState.currentPage != 0 }
    }

    val isLastPage by remember {
        derivedStateOf { pagerState.targetPage == pagerState.pageCount - 1 }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MemorizeTheme.colors.primaryBackground,
        topBar = {
            MemorizeTopBar(
                title = stringResource(id = R.string.results_title, 1),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            FooterContent(
                isComplete = {
                    isLastPage
                },
                isPrevEnabled = {
                    isPrevEnabled
                },
                onNextClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onPrevClick = {
                    scope.launch {
                        if (pagerState.currentPage != 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                onCompleteClick = component::onGoHomeClick
            )
        }
    ) { innerPadding ->
        ResultsContent(
            details = uiState.details,
            correctCount = uiState.correctCount,
            totalCount = uiState.totalCount,
            scorePercentage = uiState.scorePercentage,
            itemPerPage = uiState.itemPerPage,
            pagerState = pagerState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun PreviewResultsScreen() {
    MemorizeTheme {
        ResultsScreen(
            component = FakeResultsComponent(),
            onBackClick = {}
        )
    }
}