package dev.renheyzer.memorize.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val lightPrimary = Color(0xFF4B5C92)
private val lightOnPrimary = Color(0xFFFFFFFF)
private val lightPrimaryContainer = Color(0xFFDFE1FF)
private val lightOnPrimaryContainer = Color(0xFF001453)

private val lightSecondary = Color(0xFF765A0B)
private val lightOnSecondary = Color(0xFFFFFFFF)
private val lightSecondaryContainer = Color(0xFFFFDF99)
private val lightOnSecondaryContainer = Color(0xFF251A00)

private val lightTertiary = Color(0xFF904A44)
private val lightOnTertiary = Color(0xFFFFFFFF)
private val lightTertiaryContainer = Color(0xFFFFDAD6)
private val lightOnTertiaryContainer = Color(0xFF3B0907)

private val lightError = Color(0xFFBA1A1A)
private val lightOnError = Color(0xFFFFFFFF)
private val lightErrorContainer = Color(0xFFFFDAD6)
private val lightOnErrorContainer = Color(0xFF410002)

private val lightBackground = Color(0xFFFAF8FF) // surface bright
private val lightOnBackground = Color(0xFF1A1B21) // on surface
private val lightSurface = Color(0xFFFAF8FF) // surface
private val lightOnSurface = Color(0xFF1A1B21) // on surface
private val lightSurfaceVariant = Color(0xFFEEEDF4) // surf container
private val lightOnSurfaceVariant = Color(0xFF45464F) // on surf variant

private val lightSurfaceContainerLowest = Color(0xFFFFFFFF)
private val lightSurfaceContainerLow = Color(0xFFF4F3FA)
private val lightSurfaceContainer = Color(0xFFEEEDF4)
private val lightSurfaceContainerHigh = Color(0xFFE9E7EF)
private val lightSurfaceContainerHighest = Color(0xFFE3E1E9)

private val lightOutline = Color(0xFF757680) // outline
private val lightOutlineVariant = Color(0xFFC5C6D0) // outline variant

private val lightInverseSurface = Color(0xFF2F3036) // inverse surface
private val lightInverseOnSurface = Color(0xFFF1F0F7) // inverse on surface
private val lightInversePrimary = Color(0xFFB4C5FF) // inverse primary
private val lightScrim = Color(0xFF000000) // scrim

val lightSuccess = Color(0xFF386B44)
val lightOnSuccess = Color(0xFFFFFFFF)
val lightSuccessContainer = Color(0xFFC7F3C1)
val lightOnSuccessContainer = Color(0xFF002206)

private val darkPrimary = Color(0xFFB4C5FF)
private val darkOnPrimary = Color(0xFF1B2D61)
private val darkPrimaryContainer = Color(0xFF334478)
private val darkOnPrimaryContainer = Color(0xFFDBE1FF)

private val darkSecondary = Color(0xFFE7C26C)
private val darkOnSecondary = Color(0xFF3F2E00)
private val darkSecondaryContainer = Color(0xFF5A4300)
private val darkOnSecondaryContainer = Color(0xFFFFDF9A)

private val darkTertiary = Color(0xFFFFB4AC)
private val darkOnTertiary = Color(0xFF561E1A)
private val darkTertiaryContainer = Color(0xFF73332e)
private val darkOnTertiaryContainer = Color(0xFFFFDAD6)

private val darkError = Color(0xFFFFB4AB)
private val darkOnError = Color(0xFF561E19)
private val darkErrorContainer = Color(0xFF73342D)
private val darkOnErrorContainer = Color(0xFFFFDAD5)

private val darkBackground = Color(0xFF38393F) // surface bright
private val darkOnBackground = Color(0xFFE3E1E9) // on surface
private val darkSurface = Color(0xFF121318) // surface
private val darkOnSurface = Color(0xFFE3E1E9) // on surface
private val darkSurfaceVariant = Color(0xFF1E1F25) // surf container
private val darkOnSurfaceVariant = Color(0xFFC5C6D0) // on surf variant

private val darkSurfaceContainerLowest = Color(0xFF0D0E13)
private val darkSurfaceContainerLow = Color(0xFF1A1B21)
private val darkSurfaceContainer = Color(0xFF1E1F25)
private val darkSurfaceContainerHigh = Color(0xFF292A2F)
private val darkSurfaceContainerHighest = Color(0xFF34343A)

private val darkOutline = Color(0xFF8F9099) // outline
private val darkOutlineVariant = Color(0xFF44464F) // outline variant

private val darkInverseSurface = Color(0xFFE3E1E9) // inverse surface
private val darkInverseOnSurface = Color(0xFF2F3036) // inverse on surface
private val darkInversePrimary = Color(0xFF4B5C92) // inverse primary
private val darkScrim = Color(0xFF000000) // scrim

val darkSuccess = Color(0xFF9CD5A1)
val darkOnSuccess = Color(0xFF003912)
val darkSuccessContainer = Color(0xFF1B5129)
val darkOnSuccessContainer = Color(0xFFB8F2BC)

@Immutable
data class SuccessColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color
)

val LocalSuccessColors = staticCompositionLocalOf {
    SuccessColors(
        success = lightSuccess,
        onSuccess = lightOnSuccess,
        successContainer = lightSuccessContainer,
        onSuccessContainer = lightOnSuccessContainer
    )
}

val MaterialTheme.successColors: SuccessColors
    @Composable
    @ReadOnlyComposable
    get() = LocalSuccessColors.current

val darkColorScheme = darkColorScheme(
    primary = darkPrimary,
    onPrimary = darkOnPrimary,
    primaryContainer = darkPrimaryContainer,
    onPrimaryContainer = darkOnPrimaryContainer,
    secondary = darkSecondary,
    onSecondary = darkOnSecondary,
    secondaryContainer = darkSecondaryContainer,
    onSecondaryContainer = darkOnSecondaryContainer,
    tertiary = darkTertiary,
    onTertiary = darkOnTertiary,
    tertiaryContainer = darkTertiaryContainer,
    onTertiaryContainer = darkOnTertiaryContainer,
    error = darkError,
    onError = darkOnError,
    errorContainer = darkErrorContainer,
    onErrorContainer = darkOnErrorContainer,
    background = darkBackground,
    onBackground = darkOnBackground,
    surface = darkSurface,
    onSurface = darkOnSurface,
    surfaceVariant = darkSurfaceVariant,
    onSurfaceVariant = darkOnSurfaceVariant,
    surfaceContainerLowest = darkSurfaceContainerLowest,
    surfaceContainerLow = darkSurfaceContainerLow,
    surfaceContainer = darkSurfaceContainer,
    surfaceContainerHigh = darkSurfaceContainerHigh,
    surfaceContainerHighest = darkSurfaceContainerHighest,
    outline = darkOutline,
    outlineVariant = darkOutlineVariant,
    inverseSurface = darkInverseSurface,
    inverseOnSurface = darkInverseOnSurface,
    inversePrimary = darkInversePrimary,
    scrim = darkScrim,
)

val lightColorScheme = lightColorScheme(
    primary = lightPrimary,
    onPrimary = lightOnPrimary,
    primaryContainer = lightPrimaryContainer,
    onPrimaryContainer = lightOnPrimaryContainer,
    secondary = lightSecondary,
    onSecondary = lightOnSecondary,
    secondaryContainer = lightSecondaryContainer,
    onSecondaryContainer = lightOnSecondaryContainer,
    tertiary = lightTertiary,
    onTertiary = lightOnTertiary,
    tertiaryContainer = lightTertiaryContainer,
    onTertiaryContainer = lightOnTertiaryContainer,
    error = lightError,
    onError = lightOnError,
    errorContainer = lightErrorContainer,
    onErrorContainer = lightOnErrorContainer,
    background = lightBackground,
    onBackground = lightOnBackground,
    surface = lightSurface,
    onSurface = lightOnSurface,
    surfaceVariant = lightSurfaceVariant,
    onSurfaceVariant = lightOnSurfaceVariant,
    surfaceContainerLowest = lightSurfaceContainerLowest,
    surfaceContainerLow = lightSurfaceContainerLow,
    surfaceContainer = lightSurfaceContainer,
    surfaceContainerHigh = lightSurfaceContainerHigh,
    surfaceContainerHighest = lightSurfaceContainerHighest,
    outline = lightOutline,
    outlineVariant = lightOutlineVariant,
    inverseSurface = lightInverseSurface,
    inverseOnSurface = lightInverseOnSurface,
    inversePrimary = lightInversePrimary,
    scrim = lightScrim,
)