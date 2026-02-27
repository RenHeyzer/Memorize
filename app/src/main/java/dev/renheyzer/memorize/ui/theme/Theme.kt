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

    val primaryHeading = TextStyle(
        fontSize = when (textSize) {
            MemorizeSize.Small -> 24.sp
            MemorizeSize.Medium -> 28.sp
            MemorizeSize.Big -> 32.sp
        },
        fontFamily = rubikFamily,
        fontWeight = FontWeight.Bold
    )

    val typography = MemorizeTypography(
        primaryHeading = primaryHeading,
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
            fontWeight = FontWeight.Bold
        ),
        buttonLarge = primaryHeading.copy(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 16.sp
                MemorizeSize.Medium -> 20.sp
                MemorizeSize.Big -> 24.sp
            }
        ),
        display = primaryHeading.copy(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 40.sp
                MemorizeSize.Medium -> 48.sp
                MemorizeSize.Big -> 60.sp
            }
        )
    )

    val shape = MemorizeShape(
        topBar = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            MemorizeCorner.Big -> RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            MemorizeCorner.Full -> RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
        },
        card = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(15)
            MemorizeCorner.Medium -> RoundedCornerShape(20)
            MemorizeCorner.Big -> RoundedCornerShape(25)
            MemorizeCorner.Full -> RoundedCornerShape(30)
        },
        textField = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(8.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(12.dp)
            MemorizeCorner.Big -> RoundedCornerShape(16.dp)
            MemorizeCorner.Full -> CircleShape
        },
        button = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(8.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(12.dp)
            MemorizeCorner.Big -> RoundedCornerShape(16.dp)
            MemorizeCorner.Full -> CircleShape
        },
        buttonLarge = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(16.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(24.dp)
            MemorizeCorner.Big -> RoundedCornerShape(32.dp)
            MemorizeCorner.Full -> CircleShape
        },
        small = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(4.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(6.dp)
            MemorizeCorner.Big -> RoundedCornerShape(8.dp)
            MemorizeCorner.Full -> CircleShape
        },
        bottomSheet = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            MemorizeCorner.Big -> RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            MemorizeCorner.Full -> RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        }
    )

    CompositionLocalProvider(
        LocalMemorizeColors provides colorScheme,
        LocalMemorizeTypography provides typography,
        LocalMemorizeShape provides shape,
        content = content
    )
}