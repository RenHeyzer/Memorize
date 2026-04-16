package dev.renheyzer.memorize.feature.core.home.presentation.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.core.components.core.home.HomeComponent
import dev.renheyzer.memorize.feature.core.home.presentation.ui.home.components.HomeContent

@Composable
fun HomeScreen(component: HomeComponent, modifier: Modifier = Modifier) {
    HomeContent(modifier = Modifier.padding(16.dp))
}