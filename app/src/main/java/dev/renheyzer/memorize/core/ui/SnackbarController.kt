package dev.renheyzer.memorize.core.ui

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val name: String,
    val action: () -> Unit
)

class SnackbarController {

    private val _events = Channel<SnackbarEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}

val LocalSnackbarController =
    compositionLocalOf<SnackbarController> { error("No snackbar controller provided") }