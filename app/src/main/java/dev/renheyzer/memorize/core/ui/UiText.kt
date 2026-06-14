package dev.renheyzer.memorize.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.renheyzer.memorize.core.ui.UiText.DynamicString
import dev.renheyzer.memorize.core.ui.UiText.Joined
import dev.renheyzer.memorize.core.ui.UiText.StringResource

sealed interface UiText {

    data class DynamicString(val value: String) : UiText

    class StringResource(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText

    data class Joined(
        val items: List<UiText>,
        val separator: String = ", "
    ) : UiText
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is DynamicString -> value

        is StringResource -> {
            context.getString(resId, *args.toTypedArray())
        }

        is Joined -> {
            items.joinToString(separator) { it.asString(context) }
        }
    }
}

@Composable
fun UiText.asString(): String {
    val context = LocalContext.current
    return asString(context)
}