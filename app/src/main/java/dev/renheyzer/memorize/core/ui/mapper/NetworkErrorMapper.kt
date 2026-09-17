package dev.renheyzer.memorize.core.ui.mapper

import dev.renheyzer.memorize.R
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.ui.UiText

fun NetworkError.toUiText(): UiText {
    return when (this) {
        NetworkError.NoInternet -> UiText.StringResource(R.string.error_network_unavailable)
        NetworkError.ServerError -> UiText.StringResource(R.string.error_server)
        NetworkError.ServiceUnavailable -> UiText.StringResource(R.string.error_service_unavailable)
        NetworkError.Unauthorized -> UiText.StringResource(R.string.error_unauthorized)
        NetworkError.Timeout -> UiText.StringResource(R.string.error_network_timeout)
        NetworkError.NotFound -> UiText.StringResource(R.string.error_not_found)
        is NetworkError.Unknown -> UiText.StringResource(R.string.error_network_unknown)
    }
}