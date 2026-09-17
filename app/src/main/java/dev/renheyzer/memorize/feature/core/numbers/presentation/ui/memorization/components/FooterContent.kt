package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.component.MemorizeActionButton
import dev.renheyzer.memorize.core.ui.component.MemorizeActionIconButton
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.successColors

@Composable
fun FooterContent(
    modifier: Modifier = Modifier,
    isCompleteEnabled: Boolean = true,
    isComplete: () -> Boolean,
    isPrevEnabled: () -> Boolean,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.large,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MemorizeActionIconButton(
                modifier = Modifier
                    .widthIn(min = 130.dp, max = 250.dp)
                    .height(70.dp),
                iconRes = R.drawable.ic_chevron_backward_24,
                onClick = onPrevClick,
                defaultElevation = 8.dp,
                enabled = isPrevEnabled(),
                contentDescription = stringResource(R.string.back)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Surface(
                shape = MaterialTheme.shapes.large,
                shadowElevation = 8.dp,
            ) {
                Crossfade(
                    targetState = isComplete(),
                    label = "Button Swap Animation"
                ) { complete ->
                    if (complete) {
                        MemorizeActionButton(
                            onClick = onCompleteClick,
                            text = stringResource(R.string.complete),
                            defaultElevation = 0.dp,
                            modifier = Modifier.widthIn(min = 200.dp, max = 320.dp),
                            containerColor = MaterialTheme.successColors.success,
                            addition = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                                    contentDescription = stringResource(R.string.complete),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            enabled = isCompleteEnabled
                        )
                    } else {
                        MemorizeActionButton(
                            onClick = onNextClick,
                            text = stringResource(R.string.next),
                            defaultElevation = 0.dp,
                            modifier = Modifier.widthIn(min = 200.dp, max = 320.dp),
                            addition = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_forward_24),
                                    contentDescription = stringResource(R.string.next),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFooterContent() {
    MemorizeTheme {
        FooterContent(
            isCompleteEnabled = true,
            isComplete = { true },
            isPrevEnabled = { false },
            onNextClick = {},
            onPrevClick = {},
            onCompleteClick = {}
        )
    }
}