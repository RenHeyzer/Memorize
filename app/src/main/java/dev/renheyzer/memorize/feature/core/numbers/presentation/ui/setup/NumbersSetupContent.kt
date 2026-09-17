package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.setup

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeActionButton
import dev.renheyzer.memorize.core.ui.component.MemorizeSwitch
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.setup.NumbersSetupState
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.spacings
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumbersSetupContent(
    modifier: Modifier = Modifier,
    state: NumbersSetupState,
    onRoundsSelected: (Int) -> Unit,
    onMemorizeTimeChanged: (Int) -> Unit,
    onBinaryToggled: (Boolean) -> Unit,
    onGridSelected: (columns: Int, rows: Int) -> Unit,
    onStartClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacings.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.numbers_setup_is_binary_label),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleSmall
        )

        MemorizeSwitch(
            checked = state.isBinary,
            onCheckedChange = {
                onBinaryToggled(it)
            }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacings.large))

        val value = state.rounds ?: 1

        SetupSlider(value = value, steps = 8, valueRange = 1f..10f) { value ->
            onRoundsSelected(value.roundToInt())
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacings.large))

        if (!state.isBinary) {
            val value = state.memorizeTime ?: 3

            SetupSlider(value = value, steps = 26, valueRange = 3f..30f) { changedValue ->
                onMemorizeTimeChanged(changedValue.roundToInt())
            }
        } else {
            val value = state.memorizeTime ?: 2

            SetupSlider(value = value, steps = 12, valueRange = 2f..15f) { changedValue ->
                onMemorizeTimeChanged(changedValue.roundToInt())
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacings.large))

        val gridSizes = rememberSaveable { mutableStateListOf(2 to 2, 3 to 3, 4 to 4) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacings.small)
        ) {
            gridSizes.forEach { pair ->
                var cardContainerColor = MaterialTheme.colorScheme.surface
                var cardContentColor = MaterialTheme.colorScheme.onSurface
                var cardBorderColor = MaterialTheme.colorScheme.outlineVariant
                if (state.columns == pair.first && state.rows == pair.second) {
                    cardContainerColor = MaterialTheme.colorScheme.primaryContainer
                    cardContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    cardBorderColor = MaterialTheme.colorScheme.primary
                }
                OutlinedCard(
                    onClick = { onGridSelected(pair.first, pair.second) },
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = cardContainerColor,
                        contentColor = cardContentColor,
                    ),
                    border = BorderStroke(width = 1.dp, color = cardBorderColor)
                ) {
                    Text(
                        text = "${pair.first}×${pair.second}",
                        modifier = Modifier.padding(MaterialTheme.spacings.small),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }

        MemorizeActionButton(
            modifier = Modifier
                .padding(32.dp)
                .widthIn(min = 130.dp, max = 250.dp)
                .height(70.dp),
            text = stringResource(R.string.numbers_setup_start_action),
            onClick = onStartClicked,
            defaultElevation = 8.dp,
            enabled = state.isStartButtonEnabled,
            addition = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_forward_24),
                    contentDescription = stringResource(R.string.numbers_setup_start_button_content_description),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupSlider(
    value: Int,
    steps: Int = 0,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (Float) -> Unit,
) {
    val sliderState = rememberSliderState(value = value.toFloat(), steps = steps, valueRange = valueRange)

    LaunchedEffect(value, valueRange) {
        sliderState.value = value.toFloat()
    }

    sliderState.onValueChange = onValueChange

    Slider(state = sliderState)

    Text(
        text = value.toString(),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNumbersSetupContent() {
    MemorizeTheme {
        NumbersSetupContent(
            state = NumbersSetupState(rounds = 5, memorizeTime = 10),
            onRoundsSelected = {},
            onMemorizeTimeChanged = {},
            onBinaryToggled = {},
            onGridSelected = { _, _ -> },
            onStartClicked = {},
        )
    }
}