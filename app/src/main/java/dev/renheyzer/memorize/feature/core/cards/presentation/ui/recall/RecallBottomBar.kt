package dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.ui.theme.successColors

@Composable
fun RecallBottomBar(
    onCheckClick: () -> Unit,
    isComplete: Boolean,
    isCheckClickEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Button(
            onClick = onCheckClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = if (isComplete) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.successColors.success,
                    contentColor = Color.White
                )
            } else {
                ButtonDefaults.buttonColors()
            },
            enabled = isCheckClickEnabled
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.check_results_label),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}