package dev.renheyzer.memorize.presentation.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.components.auth.login.Login

@Composable
fun LoginScreen(
    component: Login,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Text("Login")
    }
}