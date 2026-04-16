package dev.renheyzer.memorize.feature.core.home.presentation.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.sizeIn(
            minWidth = 300.dp,
            minHeight = 300.dp,
            maxWidth = 400.dp,
            maxHeight = 400.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Numbers")
                }
            }

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Pictures")
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Card")
                }
            }

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Morse code")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {
    MemorizeTheme {
        HomeContent()
    }
}