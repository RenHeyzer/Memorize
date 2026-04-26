package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization

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
import dev.renheyzer.memorize.core.components.core.numbers.memorization.MemorizationComponent
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.FooterContent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.MemorizationContent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.MemorizationItem
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun MemorizationScreen(
    component: MemorizationComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val timerState by component.timerState.collectAsStateWithLifecycle()
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
        modifier = modifier,
        containerColor = MemorizeTheme.colors.primaryBackground,
        topBar = {
            MemorizeTopBar(
                title = stringResource(id = R.string.level_title, 1),
                subtitle = stringResource(id = R.string.level_subtitle),
                onBackClick = onBackClick,
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
                onCompleteClick = component::onCompleteClick
            )
        }
    ) { innerPadding ->
        MemorizationContent(
            timerValueProvider = { timerState },
            numbers = uiState.numbers,
            itemPerPage = uiState.itemPerPage,
            pagerState = pagerState,
            modifier = Modifier.padding(innerPadding),
            itemContent = { _, number ->
                MemorizationItem(
                    number = number
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewMemorizationScreen() {
    MemorizeTheme {
        MemorizationScreen(
            modifier = Modifier.fillMaxSize(),
            component = FakeMemorizationComponent(),
            onBackClick = {},
        )
    }
}