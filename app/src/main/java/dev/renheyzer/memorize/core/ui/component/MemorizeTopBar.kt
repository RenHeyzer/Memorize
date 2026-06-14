package dev.renheyzer.memorize.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

@Composable
fun MemorizeTopBar(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    isBackClickEnabled: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        color = MemorizeTheme.colors.secondaryBackground,
        shape = MemorizeTheme.shape.topBar,
        shadowElevation = 8.dp
    ) {
        Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart),
                    enabled = isBackClickEnabled
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_24),
                        contentDescription = stringResource(R.string.back),
                        tint = MemorizeTheme.colors.primaryText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MemorizeTheme.typography.toolbar,
                    color = MemorizeTheme.colors.primaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = subtitle,
                        textAlign = TextAlign.Center,
                        style = MemorizeTheme.typography.body,
                        color = MemorizeTheme.colors.tertiaryText,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMemorizeTopBar() {
    MemorizeTheme {
        MemorizeTopBar(
            title = "Level1",
            subtitle = "Remember the arrangement of the numbers",
            onBackClick = {}
        )
    }
}