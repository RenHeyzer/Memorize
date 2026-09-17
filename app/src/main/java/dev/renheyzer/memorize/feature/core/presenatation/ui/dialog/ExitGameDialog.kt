package dev.renheyzer.memorize.feature.core.presenatation.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.ui.AdaptiveDevicePreviews
import dev.renheyzer.memorize.core.ui.ThemePreviews
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import dev.renheyzer.memorize.ui.theme.successColors

@Composable
fun ConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_info),
                contentDescription = "Info"
            )
        },
        title = {
            Text(text = stringResource(id = R.string.dialog_exit_title))
        },
        text = {
            Text(text = stringResource(id = R.string.dialog_exit_text))
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(text = stringResource(R.string.dialog_btn_confirm), color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(R.string.dialog_btn_dismiss), color = MaterialTheme.successColors.success)
            }
        },
        modifier = modifier
    )
}

@ThemePreviews
@AdaptiveDevicePreviews
@Composable
private fun PreviewConfirmationDialog() {
    MemorizeTheme {
        ConfirmationDialog(
            onDismiss = {},
            onConfirm = {},
        )
    }
}