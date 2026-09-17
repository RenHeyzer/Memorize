package dev.renheyzer.memorize.feature.core.cards.presentation.ui.setup

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeTopBar
import dev.renheyzer.memorize.feature.core.cards.presentation.component.setup.CardsSetupComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.store.setup.CardsSetupIntent

@Composable
fun CardsSetupScreen(
    component: CardsSetupComponent,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            MemorizeTopBar(
                title = stringResource(R.string.cards_setup_title),
                isBackClickEnabled = !state.isFinished,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        CardsSetupContent(
            selectedSuits = state.selectedSuits,
            onRememberTimeChanged = {
                component.onIntent(
                    CardsSetupIntent.OnRememberTimeChanged(
                        rememberTimeInput = it
                    )
                )
            },
            onSuitSelected = { component.onIntent(CardsSetupIntent.OnSuitToggled(suit = it)) },
            isStartButtonEnabled = state.isStartButtonEnabled,
            onStartClicked = { component.onIntent(CardsSetupIntent.OnStartClicked) },
            suitsError = state.suitsError,
            rememberTimeError = state.rememberTimeError,
            modifier = Modifier.padding(innerPadding),
        )
    }
}