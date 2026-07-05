package dev.renheyzer.memorize.feature.core.cards.presentation.ui.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.UiText
import dev.renheyzer.memorize.core.ui.asString
import dev.renheyzer.memorize.core.ui.component.MemorizeActionButton
import dev.renheyzer.memorize.core.ui.component.MemorizeOutlinedTextField
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CardsSetupContent(
    selectedSuits: List<CardSuit>,
    onRememberTimeChanged: (String) -> Unit,
    onSuitSelected: (CardSuit) -> Unit,
    isStartButtonEnabled: Boolean,
    onStartClicked: () -> Unit,
    modifier: Modifier = Modifier,
    rememberTimeError: UiText? = null,
    suitsError: UiText? = null
) {
    val rememberTimeState = rememberTextFieldState()

    LaunchedEffect(rememberTimeState) {
        snapshotFlow { rememberTimeState.text }
            .collectLatest { newText ->
                val rememberTime = newText.toString()
                onRememberTimeChanged(rememberTime)
            }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isRememberTimeError = rememberTimeError?.asString()?.isNotBlank() ?: false

        Text(
            text = suitsError?.asString() ?: stringResource(R.string.cards_setup_suits_label),
            modifier = Modifier.padding(bottom = 16.dp),
            color = if (suitsError == null) MemorizeTheme.colors.primaryText else MemorizeTheme.colors.errorColor,
            style = MemorizeTheme.typography.body
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .widthIn(max = 560.dp)
                .height(100.dp)
        ) {
            items(items = CardSuit.entries, key = { it.baseId }) { suit ->
                val isSelected = selectedSuits.contains(suit)

                SuitItem(
                    suit = suit,
                    onSelect = { onSuitSelected(suit) },
                    isSelected = isSelected,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        MemorizeOutlinedTextField(
            state = rememberTimeState,
            modifier = Modifier
                .padding(top = 16.dp)
                .width(124.dp),
            textStyle = MemorizeTheme.typography.body.copy(textAlign = TextAlign.Center),
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.setup_remember_time_field_label),
                    style = MemorizeTheme.typography.body.copy(textAlign = TextAlign.Center)
                )
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.setup_time_input_placeholder),
                    style = MemorizeTheme.typography.body.copy(textAlign = TextAlign.Center)
                )
            },
            errorMessage = rememberTimeError,
            isError = isRememberTimeError,
            inputTransformation = InputTransformation.maxLength(4).then {
                if (!asCharSequence().isDigitsOnly()) {
                    revertAllChanges()
                }
            },
            outputTransformation = OutputTransformation {
                if (length > 2) {
                    insert(length - 2, ":")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        MemorizeActionButton(
            modifier = Modifier
                .padding(32.dp)
                .widthIn(min = 130.dp, max = 250.dp)
                .height(70.dp),
            text = stringResource(R.string.numbers_setup_start_action),
            onClick = onStartClicked,
            defaultElevation = 8.dp,
            enabled = isStartButtonEnabled,
            addition = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_forward_24),
                    contentDescription = stringResource(R.string.numbers_setup_start_button_content_description),
                    tint = MemorizeTheme.colors.onAccentText,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardsSetupContent() {
    MemorizeTheme {
        CardsSetupContent(
            selectedSuits = emptyList(),
            onRememberTimeChanged = {},
            onSuitSelected = {},
            isStartButtonEnabled = true,
            onStartClicked = {},
        )
    }
}