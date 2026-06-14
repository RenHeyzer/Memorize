package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.setup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.FakeNumbersSetupComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.NumbersSetupComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupIntent
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun NumbersSetupScreen(
    component: NumbersSetupComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MemorizeTheme.colors.primaryBackground,
        topBar = {
            MemorizeTopBar(
                title = stringResource(R.string.numbers_setup_title),
                subtitle = stringResource(R.string.numbers_setup_subtitle),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        NumbersSetupContent(
            modifier = Modifier.padding(innerPadding),
            isBinary = uiState.isBinary,
            isStartButtonEnabled = uiState.isStartButtonEnabled,
            onQuantityChanged = { component.onIntent(intent = NumbersSetupIntent.OnQuantityChanged(it)) },
            onRememberTimeChanged = { timeInput ->
                component.onIntent(intent = NumbersSetupIntent.OnRememberTimeChanged(rememberTimeInput = timeInput))
            },
            onBinaryToggled = { component.onIntent(intent = NumbersSetupIntent.OnBinaryToggled(it)) },
            onStartClicked = { component.onIntent(intent = NumbersSetupIntent.OnStartClicked) },
            quantityError = uiState.quantityError,
            rememberTimeError = uiState.rememberTimeError
        )
    }
}

@Preview
@Composable
fun PreviewNumbersSetupScreen() {
    MemorizeTheme {
        NumbersSetupScreen(
            component = FakeNumbersSetupComponent(),
            onBackClick = {}
        )
    }
}