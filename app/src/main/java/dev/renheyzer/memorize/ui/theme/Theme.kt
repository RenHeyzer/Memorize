package dev.renheyzer.memorize.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
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
    spacingSize: MemorizeSpacingSize = MemorizeSpacingSize.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val successColors = when {
        darkTheme -> SuccessColors(
            success = darkSuccess,
            onSuccess = darkOnSuccess,
            successContainer = darkSuccessContainer,
            onSuccessContainer = darkOnSuccessContainer
        )

        else -> SuccessColors(
            success = lightSuccess,
            onSuccess = lightOnSuccess,
            successContainer = lightSuccessContainer,
            onSuccessContainer = lightOnSuccessContainer
        )
    }

    val typography = Typography(
        // DISPLAY (Крупный игровой таймер, счет очков)
        displayLarge = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 44.sp
                MemorizeSize.Medium -> 57.sp
                MemorizeSize.Big -> 64.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 44.sp
                MemorizeSize.Medium -> 57.sp
                MemorizeSize.Big -> 64.sp
            },
            letterSpacing = (-0.25).sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Bold
        ),
        displayMedium = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 36.sp
                MemorizeSize.Medium -> 45.sp
                MemorizeSize.Big -> 52.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 44.sp
                MemorizeSize.Medium -> 52.sp
                MemorizeSize.Big -> 60.sp
            },
            letterSpacing = 0.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        displaySmall = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 28.sp
                MemorizeSize.Medium -> 36.sp
                MemorizeSize.Big -> 40.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 36.sp
                MemorizeSize.Medium -> 44.sp
                MemorizeSize.Big -> 48.sp
            },
            letterSpacing = 0.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),

        // HEADLINE (Названия игровых экранов, крупные заголовки разделов)
        headlineLarge = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 24.sp
                MemorizeSize.Medium -> 32.sp
                MemorizeSize.Big -> 36.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 32.sp
                MemorizeSize.Medium -> 40.sp
                MemorizeSize.Big -> 44.sp
            },
            letterSpacing = 0.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Bold
        ),
        headlineMedium = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 22.sp
                MemorizeSize.Medium -> 28.sp
                MemorizeSize.Big -> 32.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 28.sp
                MemorizeSize.Medium -> 36.sp
                MemorizeSize.Big -> 40.sp
            },
            letterSpacing = 0.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        headlineSmall = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 20.sp
                MemorizeSize.Medium -> 24.sp
                MemorizeSize.Big -> 28.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 26.sp
                MemorizeSize.Medium -> 32.sp
                MemorizeSize.Big -> 36.sp
            },
            letterSpacing = 0.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.SemiBold
        ),

        // TITLE (Текст на игральных картах, значения в ячейках, заголовки в диалогах)
        titleLarge = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 18.sp
                MemorizeSize.Medium -> 22.sp
                MemorizeSize.Big -> 24.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 24.sp
                MemorizeSize.Medium -> 28.sp
                MemorizeSize.Big -> 32.sp
            },
            letterSpacing = 0.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        titleMedium = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 20.sp
                MemorizeSize.Medium -> 24.sp
                MemorizeSize.Big -> 26.sp
            },
            letterSpacing = 0.15.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        titleSmall = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 12.sp
                MemorizeSize.Medium -> 14.sp
                MemorizeSize.Big -> 15.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 16.sp
                MemorizeSize.Medium -> 20.sp
                MemorizeSize.Big -> 22.sp
            },
            letterSpacing = 0.1.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),

        // BODY (Правила игры, описания, инструкции)
        bodyLarge = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 20.sp
                MemorizeSize.Medium -> 24.sp
                MemorizeSize.Big -> 28.sp
            },
            letterSpacing = 0.5.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Normal
        ),
        bodyMedium = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 12.sp
                MemorizeSize.Medium -> 14.sp
                MemorizeSize.Big -> 16.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 16.sp
                MemorizeSize.Medium -> 20.sp
                MemorizeSize.Big -> 24.sp
            },
            letterSpacing = 0.25.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Normal
        ),
        bodySmall = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 10.sp
                MemorizeSize.Medium -> 12.sp
                MemorizeSize.Big -> 13.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            letterSpacing = 0.4.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Normal
        ),

        // LABEL (Интерактивные элементы, текст на кнопках "Завершить", второстепенные чипсы)
        labelLarge = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 12.sp
                MemorizeSize.Medium -> 14.sp
                MemorizeSize.Big -> 16.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 16.sp
                MemorizeSize.Medium -> 20.sp
                MemorizeSize.Big -> 24.sp
            },
            letterSpacing = 0.1.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        labelMedium = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 11.sp
                MemorizeSize.Medium -> 12.sp
                MemorizeSize.Big -> 13.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 14.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 18.sp
            },
            letterSpacing = 0.5.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        ),
        labelSmall = TextStyle(
            fontSize = when (textSize) {
                MemorizeSize.Small -> 9.sp
                MemorizeSize.Medium -> 11.sp
                MemorizeSize.Big -> 12.sp
            },
            lineHeight = when (textSize) {
                MemorizeSize.Small -> 12.sp
                MemorizeSize.Medium -> 16.sp
                MemorizeSize.Big -> 16.sp
            },
            letterSpacing = 0.5.sp,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        )
    )

    val shape = Shapes(
        extraSmall = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(4.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(6.dp)
            MemorizeCorner.Big -> RoundedCornerShape(8.dp)
            MemorizeCorner.Full -> CircleShape
        },
        small = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(4.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(6.dp)
            MemorizeCorner.Big -> RoundedCornerShape(8.dp)
            MemorizeCorner.Full -> CircleShape
        },
        // medium — основной игровой слот (игральные карты, ячейки чисел, картинки)
        medium = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(12.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(16.dp)
            MemorizeCorner.Big -> RoundedCornerShape(24.dp)
            MemorizeCorner.Full -> RoundedCornerShape(32.dp)
        },
        large = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(16.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(24.dp)
            MemorizeCorner.Big -> RoundedCornerShape(32.dp)
            MemorizeCorner.Full -> CircleShape
        },
        extraLarge = when (corner) {
            MemorizeCorner.Small -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            MemorizeCorner.Medium -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            MemorizeCorner.Big -> RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            MemorizeCorner.Full -> RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        }
    )

    val spacings = when (spacingSize) {
        MemorizeSpacingSize.Small -> MemorizeSpacing(
            extraSmall = 2.dp,
            small = 6.dp,
            medium = 12.dp,
            large = 18.dp,
            extraLarge = 24.dp,
            giant = 36.dp
        )

        MemorizeSpacingSize.Medium -> MemorizeSpacing()

        MemorizeSpacingSize.Big -> MemorizeSpacing(
            extraSmall = 6.dp,
            small = 12.dp,
            medium = 20.dp,
            large = 32.dp,
            extraLarge = 40.dp,
            giant = 56.dp
        )
    }

    CompositionLocalProvider(
        LocalSuccessColors provides successColors,
        LocalMemorizeSpacing provides spacings
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shape,
            content = content
        )
    }
}

enum class MemorizeSize {
    Small, Medium, Big
}

enum class MemorizeCorner {
    Small, Medium, Big, Full
}

enum class MemorizeSpacingSize {
    Small, Medium, Big
}