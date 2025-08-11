package dev.renheyzer.memorize.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.components.home.Home

@Composable
fun HomeScreen(modifier: Modifier = Modifier, component: Home) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text("Home")
    }
}