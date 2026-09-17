package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.setup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
        containerColor = MaterialTheme.colorScheme.background,
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
            state = uiState,
            onRoundsSelected = { rounds ->
                component.onIntent(
                    intent = NumbersSetupIntent.OnNumberOfRoundsSelected(
                        rounds
                    )
                )
            },
            onMemorizeTimeChanged = { timeInput ->
                component.onIntent(
                    intent = NumbersSetupIntent.OnMemorizeTimeSelected(
                        memorizeTimeInput = timeInput
                    )
                )
            },
            onBinaryToggled = { component.onIntent(intent = NumbersSetupIntent.OnBinaryToggled(it)) },
            onGridSelected = { column, row ->
                component.onIntent(
                    intent = NumbersSetupIntent.OnGirdSelected(
                        columns = column,
                        rows = row
                    )
                )
            },
            onStartClicked = { component.onIntent(intent = NumbersSetupIntent.OnStartClicked) },
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