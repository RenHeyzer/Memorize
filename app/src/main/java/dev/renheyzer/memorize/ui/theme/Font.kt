package dev.renheyzer.memorize.ui.theme

import android.os.Build
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import dev.renheyzer.memorize.R

@OptIn(ExperimentalTextApi::class)
val rubikFamily = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    FontFamily(
        Font(
            resId = R.font.rubik_variable_font_wght,
            variationSettings = FontVariation.Settings(
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        ),
        Font(
            resId = R.font.rubik_variable_font_wght,
            variationSettings = FontVariation.Settings(
                weight = FontWeight.Medium,
                style = FontStyle.Normal
            )
        ),
        Font(
            resId = R.font.rubik_variable_font_wght,
            variationSettings = FontVariation.Settings(
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal
            )
        ),
        Font(
            resId = R.font.rubik_variable_font_wght,
            variationSettings = FontVariation.Settings(
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            )
        ),
        Font(
            resId = R.font.rubik_variable_font_wght,
            variationSettings = FontVariation.Settings(
                weight = FontWeight.ExtraBold,
                style = FontStyle.Normal
            )
        ),
        Font(
            resId = R.font.rubik_variable_font_wght,
            variationSettings = FontVariation.Settings(
                weight = FontWeight.Black,
                style = FontStyle.Normal
            )
        )
    )
else FontFamily(
    Font(
        resId = R.font.rubik_regular,
        variationSettings = FontVariation.Settings(
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        )
    ),
    Font(
        resId = R.font.rubik_medium,
        variationSettings = FontVariation.Settings(
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        )
    ),
    Font(
        resId = R.font.rubik_semibold,
        variationSettings = FontVariation.Settings(
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        )
    ),
    Font(
        resId = R.font.rubik_bold,
        variationSettings = FontVariation.Settings(
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        )
    ),
    Font(
        resId = R.font.rubik_extra_bold,
        variationSettings = FontVariation.Settings(
            weight = FontWeight.ExtraBold,
            style = FontStyle.Normal
        )
    ),
    Font(
        resId = R.font.rubik_black,
        variationSettings = FontVariation.Settings(
            weight = FontWeight.Black,
            style = FontStyle.Normal
        )
    )
)