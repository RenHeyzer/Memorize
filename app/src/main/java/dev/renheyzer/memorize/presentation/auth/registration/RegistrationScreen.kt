package dev.renheyzer.memorize.presentation.auth.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.components.auth.registration.Registration

@Composable
fun RegistrationScreen(
    component: Registration,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Text("Registration")
    }
}