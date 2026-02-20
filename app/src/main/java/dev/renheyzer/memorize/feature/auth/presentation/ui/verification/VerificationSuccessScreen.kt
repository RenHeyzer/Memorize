package dev.renheyzer.memorize.feature.auth.presentation.ui.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.component.MemorizeDefaultButton
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun VerificationSuccessScreen(
    message: UiText,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.congratulations),
            style = MemorizeTheme.typography.primaryHeading
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message.asString(),
            style = MemorizeTheme.typography.body
        )

        Spacer(modifier = Modifier.height(32.dp))

        MemorizeDefaultButton(
            onClick = onNext,
            text = stringResource(R.string.next),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerificationSuccessScreen() {
    MemorizeTheme {
        VerificationSuccessScreen(
            message = UiText.StringResource(R.string.verification_was_successful),
            onNext = {}
        )
    }
}