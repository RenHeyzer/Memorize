package dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R

@Composable
fun NavigationControls(
    isPrevEnabled: Boolean,
    isLastPage: Boolean,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalIconButton(
            onClick = onPrevClick,
            enabled = isPrevEnabled,
            modifier = Modifier.size(72.dp),
            shape = MaterialTheme.shapes.large,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_backward_24),
                contentDescription = stringResource(R.string.back),
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .widthIn(min = 200.dp, max = 320.dp)
                .height(72.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLastPage) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                contentColor = if (isLastPage) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            )
        ) {
            Text(
                text = stringResource(if (isLastPage) R.string.complete else R.string.next),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (isLastPage) R.drawable.ic_check else R.drawable.ic_chevron_forward_24
                ),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}