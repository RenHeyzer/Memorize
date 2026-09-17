package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
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
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall.RecallComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.recall.RecallIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.FooterContent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components.MemoryGrid
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall.components.RecallItem
import dev.renheyzer.memorize.feature.core.presenatation.ui.dialog.ConfirmationDialog
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.launch

@Composable
fun RecallScreen(
    component: RecallComponent,
    modifier: Modifier = Modifier,
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

    if (uiState.isShowExitDialog) {
        ConfirmationDialog(
            onDismiss = { component.onIntent(RecallIntent.OnExitDismissed) },
            onConfirm = { component.onIntent(RecallIntent.OnExitConfirmed) }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MemorizeTopBar(
                title = stringResource(id = R.string.round_title, 1),
                subtitle = stringResource(id = R.string.round_subtitle),
                isBackClickEnabled = !uiState.isFinished,
                onBackClick = { component.onIntent(RecallIntent.OnBackClick) }
            )
        },
        bottomBar = {
            FooterContent(
                isCompleteEnabled = !uiState.isFinished,
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
                onCompleteClick = { component.onIntent(RecallIntent.OnCompleteClick) }
            )
        }
    ) { innerPadding ->
        Spacer(Modifier.padding(innerPadding))
//        MemoryGrid(
//            timerValueProvider = { timerState },
//            numbers = uiState.answers,
//            itemPerPage = uiState.itemPerPage,
//            pagerState = pagerState,
//            modifier = Modifier.padding(innerPadding),
//            itemContent = { absoluteIndex, number ->
//                RecallItem(
//                    number = number?.toString() ?: "",
//                    onNumberChanged = { number ->
//                        component.onIntent(
//                            RecallIntent.OnUserAnswerChanged(
//                                index = absoluteIndex,
//                                answer = number
//                            )
//                        )
//                    },
//                    enabled = !uiState.isFinished
//                )
//            }
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecallScreen() {
    MemorizeTheme {
        RecallScreen(
            component = FakeRecallComponent(),
        )
    }
}