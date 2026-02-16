package dev.renheyzer.memorize.core.utils

import android.content.Context
import dev.renheyzer.memorize.core.ui.StringResolver
import dev.renheyzer.memorize.core.ui.UiText

class DefaultStringResolver(private val context: Context) : StringResolver {
    override fun resolve(text: UiText): String =
        when (text) {
            is UiText.StringResource -> text.asString(context)
            is UiText.DynamicString -> text.asString(context)
            else -> text.asString(context)
        }
}