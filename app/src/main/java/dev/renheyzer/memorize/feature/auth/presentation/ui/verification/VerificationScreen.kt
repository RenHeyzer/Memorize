package dev.renheyzer.memorize.feature.auth.presentation.ui.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.feature.auth.presentation.component.verification.Verification
import dev.renheyzer.memorize.feature.auth.presentation.ui.verification.components.VerificationContent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun VerificationScreen(
    component: Verification,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    if (uiState.isFatalError) {
        FatalErrorScreen(
            heading = UiText.StringResource(R.string.session_expired_heading),
            message = uiState.error,
            onExit = {
                component.onBackToLoginClicked()
            }
        )
    } else if (uiState.isSuccess) {
        VerificationSuccessScreen(
            message = uiState.successMessage,
            onNext = {
                component.onNextClicked()
            }
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            VerificationContent(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onResendButtonClick = {
                    component.onResendClicked()
                }
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MemorizeTheme.colors.secondaryBackground
                    )
                }
            }
        }
    }
}