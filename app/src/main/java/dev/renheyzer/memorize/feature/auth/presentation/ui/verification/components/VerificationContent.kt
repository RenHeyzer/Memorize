package dev.renheyzer.memorize.feature.auth.presentation.ui.verification.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeDefaultButton
import dev.renheyzer.memorize.feature.auth.presentation.store.verification.VerificationUiState
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun VerificationContent(
    modifier: Modifier,
    uiState: VerificationUiState,
    onResendButtonClick: () -> Unit
) {
    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        )

        Text(
            text = stringResource(R.string.verification),
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        val text = buildAnnotatedString {
            append(stringResource(R.string.check_your_email))
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.secondaryContainer)) {
                append(uiState.email)
            }
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        Text(
            text = stringResource(R.string.timer_text, uiState.timerValue),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        MemorizeDefaultButton(
            onClick = onResendButtonClick,
            text = stringResource(R.string.resend),
            enabled = uiState.isResendEnabled
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationContent() {
    MemorizeTheme {
        VerificationContent(
            modifier = Modifier.fillMaxSize(),
            uiState = VerificationUiState(),
            onResendButtonClick = {}
        )
    }
}