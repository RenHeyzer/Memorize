package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeCorner
import dev.renheyzer.memorize.ui.theme.MemorizeSize
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Preview(showBackground = true)
@Composable
fun PreviewMemorizationContent() {
    MemorizeTheme(textSize = MemorizeSize.Big, corner = MemorizeCorner.Big) {
        MemorizationContent(
            timerValueProvider = { "01:43" },
            numbers = listOf(
                22, 39, 94, 12, 39, 94, 12, 43, 89,
                12, 39, 94, 43, 98, 53, 92, 12, 34,
                12, 39, 94, 94, 43, 23, 85, 64, 85
            ),
            itemPerPage = 9,
            pagerState = rememberPagerState(pageCount = { 3 }),
            itemContent = { _, _ ->
                MemorizeTheme(textSize = MemorizeSize.Big, corner = MemorizeCorner.Big) {
                    MemorizationItem(
                        modifier = Modifier
                            .size(128.dp)
                            .padding(2.dp), number = 23
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewMemorizationItem() {
    MemorizeTheme(textSize = MemorizeSize.Big, corner = MemorizeCorner.Big) {
        MemorizationItem(
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
            timerValueProvider = { "01:43" }
        )
    }
}