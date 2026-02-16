package dev.renheyzer.memorize.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.common.AppError
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.ui.UiText.StringResource
import dev.renheyzer.memorize.feature.auth.domain.AuthError
import kotlinx.serialization.Serializable

@Serializable
sealed class UiText {

    data object Empty : UiText()

    @Serializable
    data class DynamicString(val value: String) : UiText()

    @Serializable
    class StringResource(
        @param:StringRes val resId: Int,
        vararg val args: UiArg
    ) : UiText()

    @Serializable
    data class Joined(
        val items: List<UiText>,
        val separator: String = ", "
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> {
                val rawArgs = args.map { it.value }.toTypedArray()
                context.getString(resId, *rawArgs)
            }
            is Joined -> items.joinToString(separator) { it.asString(context) }
            else -> ""
        }
    }

    @Composable
    fun asString(): String {
        val context = LocalContext.current
        return when (this) {
            is DynamicString -> value
            is StringResource -> {
                val rawArgs = args.map { it.value }.toTypedArray()
                context.getString(resId, *rawArgs)
            }
            is Joined -> items.joinToString(separator) { it.asString(context) }
            else -> ""
        }
    }
}

@Serializable
sealed interface UiArg {
    @Serializable data class Str(override val value: String) : UiArg
    @Serializable data class NumInt(override val value: Int) : UiArg
    @Serializable data class NumDouble(override val value: Double) : UiArg

    val value: Any get() = when(this) {
        is Str -> value
        is NumInt -> value
        is NumDouble -> value
    }
}

fun AppError.toUiText(): UiText {
    return when (this) {
        NetworkError.NoInternet -> StringResource(R.string.error_network_unavailable)
        NetworkError.ServerError -> StringResource(R.string.error_server)
        NetworkError.ServiceUnavailable -> StringResource(R.string.error_service_unavailable)
        NetworkError.Unauthorized -> StringResource(R.string.error_unauthorized)
        is NetworkError.Unknown -> StringResource(R.string.error_unknown)
        is AuthError.InvalidEmail -> StringResource(R.string.error_email_invalid)
        is AuthError.InvalidCredentials -> StringResource(R.string.error_invalid_credentials)
        is AuthError.UserCollision -> StringResource(R.string.error_user_collision)
        else -> StringResource(R.string.error_unknown)
    }
}