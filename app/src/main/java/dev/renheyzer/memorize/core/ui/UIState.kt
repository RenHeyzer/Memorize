package dev.renheyzer.memorize.core.ui

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.common.fold

sealed interface UIState<out T> {
    val data: T?

    data class Loading<T>(
        override val data: T? = null
    ) : UIState<T>

    data class Error<T>(
        val message: UiText,
        override val data: T? = null
    ) : UIState<T>

    data class Success<T>(
        override val data: T
    ) : UIState<T>
}

val <T> UIState<T>.dataOrNull: T?
    get() = when (this) {
        is UIState.Success -> data
        is UIState.Loading -> data
        else -> null
    }

val <T> UIState<T>.successDataOrNull: T?
    get() = (this as? UIState.Success)?.data

val UIState<*>.isLoading: Boolean
    get() = this is UIState.Loading

val UIState<*>.isError: Boolean
    get() = this is UIState.Error

val UIState<*>.isSuccess: Boolean
    get() = this is UIState.Success

val UIState<*>.errorMessageOrNull: UiText?
    get() = (this as? UIState.Error)?.message

inline fun <T> UIState<T>.onSuccess(action: (T) -> Unit): UIState<T> {
    if (this is UIState.Success) action(data)
    return this
}

inline fun <T> UIState<T>.onError(action: (UiText, T?) -> Unit): UIState<T> {
    if (this is UIState.Error) action(message, data)
    return this
}

inline fun <T> UIState<T>.withData(action: (T) -> Unit): UIState<T> {
    this.data?.let(action)
    return this
}

fun <T> Either<NetworkError, T>.toUiState(oldState: UIState<T>): UIState<T> {
    return fold(
        onLeft = { error ->
            UIState.Error(
                message = error.toUiText(),
                data = oldState.data
            )
        },
        onRight = { data ->
            UIState.Success(data = data)
        }
    )
}