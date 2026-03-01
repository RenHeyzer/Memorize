package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.core.components.core.numbers.memorization.store.MemorizationUiState
import dev.renheyzer.memorize.ui.theme.MemorizeCorner
import dev.renheyzer.memorize.ui.theme.MemorizeSize
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true)
@Composable
fun PreviewMemorizationContent() {
    MemorizeTheme(textSize = MemorizeSize.Big, corner = MemorizeCorner.Big) {
        MemorizationContent(
            timerState = MutableStateFlow("02:23"),
            uiState = MemorizationUiState(
                numbers = listOf(
                    listOf(22, 39, 94, 12, 39, 94, 12, 43, 89),
                    listOf(12, 39, 94, 43, 98, 53, 92, 12, 34),
                    listOf(12, 39, 94, 94, 43, 23, 85, 64, 85)
                ),
                quantity = 5,
                isRandom = true,
            ),
            pagerState = rememberPagerState(pageCount = { 3 })
        )
    }
}

@Preview
@Composable
fun PreviewNumberItem() {
    MemorizeTheme(textSize = MemorizeSize.Big, corner = MemorizeCorner.Big) {
        NumberItem(
            modifier = Modifier
                .size(128.dp)
                .padding(2.dp), number = 23
        )
    }
}

@Preview
@Composable
fun PreviewTimerContent() {
    MemorizeTheme {
        TimerContent(
            timerState = MutableStateFlow("02:23")
        )
    }
}