package dev.renheyzer.memorize.feature.auth.presentation.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.renheyzer.memorize.core.ui.component.MemorizeDefaultButton
import dev.renheyzer.memorize.feature.auth.presentation.component.login.Login

@Composable
fun LoginScreen(
    component: Login,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("Login")
        MemorizeDefaultButton(
            onClick = {
                component.onRegistrationClicked()
            },
            text = "Go to Registration"
        )
    }
}