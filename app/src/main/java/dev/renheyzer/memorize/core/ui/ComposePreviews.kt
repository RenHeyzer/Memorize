package dev.renheyzer.memorize.core.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
annotation class ThemePreviews

@Preview(name = "Small Phone", device = "spec:width=320dp,height=640dp,dpi=420")
@Preview(name = "Standard Phone", device = "spec:width=411dp,height=891dp,dpi=420")
@Preview(name = "Foldable/Tablet", device = "spec:width=600dp,height=900dp,dpi=320")
@Preview(name = "Large Desktop", device = "spec:width=1024dp,height=768dp,dpi=240")
annotation class AdaptiveDevicePreviews