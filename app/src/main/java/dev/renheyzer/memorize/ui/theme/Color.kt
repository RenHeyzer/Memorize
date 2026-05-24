import androidx.compose.ui.graphics.Color
import dev.renheyzer.memorize.ui.theme.MemorizeColors

val baseDarkPalette = MemorizeColors(
    primaryText = Color.White,
    primaryBackground = Color.DarkGray,
    secondaryText = Color.White,
    secondaryBackground = Color.Blue,
    accentColor = Color.Blue,
    successColor = Color(0xFF10B981),
    errorColor = Color.Red,
    onAccentText = Color.White,
    borderColor = Color.Blue,
    tertiaryText = Color.LightGray,
    disabledColor = Color(0xFFE2E8F0),
    onDisabledText = Color(0xFF94A3B8)
)

val baseLightPalette = MemorizeColors(
    primaryText = Color(0xFF0F172A),
    primaryBackground = Color(0xFFF8FAFC),
    secondaryText = Color.White,
    secondaryBackground = Color.White,
    accentColor = Color(0xFF2563EB),
    successColor = Color(0xFF10B981),
    errorColor = Color.Red,
    onAccentText = Color.White,
    borderColor = Color(0xFFE2E8F0),
    tertiaryText = Color(0xFF64748B),
    disabledColor = Color(0xFFE2E8F0),
    onDisabledText = Color(0xFF94A3B8)
)