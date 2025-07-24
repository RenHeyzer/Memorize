package dev.renheyzer.memorize.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MemorizeTheme(
    textSize: MemorizeSize = MemorizeSize.Medium,
    corner: MemorizeCorner = MemorizeCorner.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> baseDarkPalette
        else -> baseLightPalette
    }

    val typography = MemorizeTypography(
        primaryHeading = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 24.sp
                MemorizeSize.Medium -> 28.sp
                MemorizeSize.Big -> 32.sp
            },
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Bold
        ),
        secondaryHeading = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 20.sp
                MemorizeSize.Medium -> 24.sp
                MemorizeSize.Big -> 28.sp
            },
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        button = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        )
    )

    val shape = MemorizeShape(
        shape = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(8.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(12.dp)
            MemorizeCorner.Big -> RoundedCornerShape(16.dp)
            MemorizeCorner.Full -> CircleShape
        }
    )

    CompositionLocalProvider(
        LocalMemorizeColors provides colorScheme,
        LocalMemorizeTypography provides typography,
        LocalMemorizeShape provides shape,
        content = content
    )
}