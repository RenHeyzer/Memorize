package dev.renheyzer.memorize.feature.auth.presentation.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.core.components.auth.registration.Registration
import dev.renheyzer.memorize.feature.auth.presentation.ui.registration.components.RegistrationContent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun RegistrationScreen(
    component: Registration,
    modifier: Modifier,
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        RegistrationContent(
            Modifier.fillMaxSize(),
            onSignUpClick = { email, password, confirmPassword ->
                component.onSignUpClick(email, password, confirmPassword)
            },
            validationError = uiState.validationError,
            onAlreadyHaveAnAccountClick = {
                component.onAlreadyHaveAnAccountClick()
            })

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MemorizeTheme.colors.secondaryBackground
                )
            }
        }
    }
}