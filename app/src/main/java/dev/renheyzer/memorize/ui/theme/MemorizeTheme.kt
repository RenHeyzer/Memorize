package dev.renheyzer.memorize.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle

data class MemorizeStyle(
    val textSize: MemorizeSize,
    val corner: MemorizeCorner,
    val isDarkMode: Boolean,
)

data class MemorizeColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val accentColor: Color,
    val successColor: Color,
    val errorColor: Color,
    val onAccentText: Color,
    val borderColor: Color,
    val tertiaryText: Color,
    val disabledColor: Color,
    val onDisabledText: Color
)

data class MemorizeTypography(
    val primaryHeading: TextStyle,
    val secondaryHeading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val button: TextStyle,
    val buttonLarge: TextStyle,
    val display: TextStyle
)

data class MemorizeShape(
    val topBar: Shape,
    val card: Shape,
    val textField: Shape,
    val button: Shape,
    val buttonLarge: Shape,
    val small: Shape,
    val bottomSheet: Shape,
)

data object MemorizeTheme {
    val colors: MemorizeColors
        @Composable
        get() = LocalMemorizeColors.current

    val typography: MemorizeTypography
        @Composable
        get() = LocalMemorizeTypography.current

    val shape: MemorizeShape
        @Composable
        get() = LocalMemorizeShape.current
}

enum class MemorizeSize {
    Small, Medium, Big
}

enum class MemorizeCorner {
    Small, Medium, Big, Full
}

val LocalMemorizeColors = staticCompositionLocalOf<MemorizeColors> {
    error("No colors provided")
}

val LocalMemorizeTypography = staticCompositionLocalOf<MemorizeTypography> {
    error("No fonts provided")
}

val LocalMemorizeShape = staticCompositionLocalOf<MemorizeShape> {
    error("No shapes provided")
}