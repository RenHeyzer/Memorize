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
    primary = Color(0xFFB4C5FF),
    onPrimary = Color(0xFF1A2C5B),
    primaryContainer = Color(0xFF324378),
    onPrimaryContainer = Color(0xFFDFE1FF),
    secondary = Color(0xFFEAA200),
    onSecondary = Color(0xFF3E2E00),
    secondaryContainer = Color(0xFF594300),
    onSecondaryContainer = Color(0xFFFFDF99),
    tertiary = Color(0xFFFFB3AB),
    onTertiary = Color(0xFF561E1A),
    tertiaryContainer = Color(0xFF73332E),
    onTertiaryContainer = Color(0xFFFFDAD6),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF121318),
    onBackground = Color(0xFFE4E1E9),
    surface = Color(0xFF121318),
    onSurface = Color(0xFFE4E1E9),
    surfaceVariant = Color(0xFF45464F),
    onSurfaceVariant = Color(0xFFC6C5D0),
    outline = Color(0xFF8F909A),
    outlineVariant = Color(0xFF45464F)
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
    outline = lightOutline,
    outlineVariant = lightOutlineVariant,
    inverseSurface = lightInverseSurface,
    inverseOnSurface = lightInverseOnSurface,
    inversePrimary = lightInversePrimary,
    scrim = lightScrim,
)